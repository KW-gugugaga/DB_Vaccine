package reservation.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reservation.vaccine.domain.Hospital;
import reservation.vaccine.domain.Location;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.domain.UserRsv;
import reservation.vaccine.service.HospitalService;
import reservation.vaccine.service.ReviewService;
import reservation.vaccine.service.UserService;
import reservation.vaccine.service.VisualizationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class PageController {

    private int checkRes = 0;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    VisualizationService visualizationService;

    @GetMapping("mainpage")
    public String GetMainPage(Model model, HttpServletRequest req) {
        System.out.println("PageController.GetMainPage");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo)user;
        String s = userInfo.toString();
        System.out.println("s = " + s);
        if(user == null) {
            System.out.println("NULL");
            return "user/login";
        } else {
            model.addAttribute("state", userInfo.getState());
            model.addAttribute("Uname", userInfo.getUname());
            System.out.println("NOT NULL");
        }

        List<Location> locations = visualizationService.visualizeByLocation();
        Map<String, Float> locationData = new HashMap<String, Float>();
        for(Location location : locations) {
            System.out.println("location.getLid() = " + location.getRatio());
            System.out.println("location.getLname() = " + location.getLname());
            locationData.put(location.getLname(), location.getRatio());
        }
        model.addAttribute("locationData", locationData);

        ArrayList<Integer> ageVaccinated = new ArrayList<Integer>();
        ArrayList<Integer> ageAll = new ArrayList<Integer>();
        Map<String, Float> AgeData = new LinkedHashMap<String, Float>();

        for(int i = 8;i>=1;i--)
        {
            ageVaccinated.add(visualizationService.findVaccinatedByAge(i));
            ageAll.add(visualizationService.findAllByAge(i));
        }

        for(int i = 0;i<8;i++) {
            System.out.println((8-i) + "0대 접종자: "+ ageVaccinated.get(i) + " " + (8-i) + "0대 전체: "+ ageAll.get(i));
        }

        for(int i = 0;i<8;i++) {
            if(ageAll.get(i)!=0) {
                float ratio = (float)(ageVaccinated.get(i)) / (ageAll.get(i));
                if (i == 0)
                    AgeData.put("80대 이상", ratio);
                else if (i == 7)
                    AgeData.put("20대 미만", ratio);
                else
                    AgeData.put((8 - i) + "0대", ratio);
            }
            else
                AgeData.put((8 - i) + "0대", 0.0f);
        }

        for(String key : AgeData.keySet()) {
            float value = (float) AgeData.get(key);
            System.out.println(key + " : " + value);
        }

        model.addAttribute("AgeData", AgeData);

        LocalDate today = LocalDate.now();
        Month monthStr = today.getMonth();
        int current_month = monthStr.getValue();

        int start_month = current_month-5;
        System.out.println("current_month = " + current_month);
        System.out.println("start_month = " + start_month);

        ArrayList<Integer> monthVaccinated = new ArrayList<Integer>();
        Map<String, Integer> MonthData = new LinkedHashMap<String, Integer>();
        Map<String, Integer> AccumulatedData = new LinkedHashMap<String, Integer>();
        for(int i= start_month;i<=current_month;i++) {
            monthVaccinated.add(visualizationService.findAllByStateDate2(i));
        }

        int accumulated=0;
        for(int i =0;i<6;i++)
        {
            accumulated+=monthVaccinated.get(i);
            MonthData.put((i+start_month)+ "월", (monthVaccinated.get(i)));
            AccumulatedData.put((i+start_month)+ "월", accumulated);
        }
        for(String key : MonthData.keySet()) {
            int value1 = (int) MonthData.get(key);
            int value2 = (int) AccumulatedData.get(key);
            System.out.println(key + " : " + value1);
            System.out.println(key + " : " + value2);
        }

        model.addAttribute("MonthData", MonthData);
        model.addAttribute("AccumulatedData", AccumulatedData);

        return "page/mainpage";
    }

    @GetMapping("hospitalpage")
    public String GetHospitalPage(Model model, HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo)user;
        int Uid = ((UserInfo) user).getUid();
        List<Hospital> hospitals = hospitalService.findAllHospitalByUid(Uid);
        for(Hospital hospital : hospitals) {
            Float avgStar = reviewService.getAvgStar(hospital.getHid());
            if(avgStar == null) {
                hospital.setAvgStar("-");
            } else {
                hospital.setAvgStar(Float.toString((float) (Math.round(avgStar*10)/10.0)));
            }
            System.out.println("avgStar = " + avgStar);
        }
        String location=setLocation(userInfo.getLid());
        location += " ";
        String full_loc=location.concat(userInfo.getRest_addr());

        System.out.println("full_loc = " + full_loc);
        model.addAttribute("hospitals", hospitals);
        model.addAttribute("user", userInfo.getUname());
        model.addAttribute("full_loc",full_loc);

        if(checkRes == 1) {
            System.out.println("이미 예약 내역 존재");
            res.setContentType("text/html; charset=euc-kr");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('예약 불가 : 회원님의 예약 내역이 이미 존재합니다.');</script>");
            out.flush();
            checkRes = 0 ;
        }
        return "page/hospitalpage";
    }

    @PostMapping("reservationpage")
    public String PostReservationPage(Model model, HttpServletRequest req, HttpServletResponse res,
                                      @RequestParam("Hid") int Hid) throws IOException {
        System.out.println("PageController.PostReservationPage");
        System.out.println("Hid = " + Hid);
        Hospital hospital = hospitalService.findHospitalByHid(Hid);
        System.out.println(hospital.toString());
        model.addAttribute("hospital", hospital);
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo)user;
        UserRsv userRsv = userService.findUserRsv(userInfo.getUid());
        Float avg = reviewService.getAvgStar(Hid);
        String avgStar;
        if(avg == null) {
            avgStar = "등록된 리뷰가 없습니다.";
        } else {
            avg = (float) (Math.round(avg*10)/10.0);
            avgStar = Float.toString(avg);
        }
        int state = 0;
        if(userRsv != null) {
            if(userRsv.getDate_2() == null) {   // 1차만 있고 2차만 없을 때
                state = 1;
            }
        }
        if(userRsv == null) {
            model.addAttribute("avgStar", avgStar);
            return "page/reservationpage";
        } else if (state == 1){
            String date_1 = userRsv.getDate_1();
            LocalDate Date_1 = LocalDate.parse(date_1, DateTimeFormatter.ISO_DATE);
            LocalDate Date_1Plus = Date_1.plusMonths(1);
            LocalDate today = LocalDate.now();
            System.out.println("Date_1 = " + Date_1);
            System.out.println("Date_1Plus = " + Date_1Plus);
            System.out.println("today = " + today);
            long min = ChronoUnit.DAYS.between(today, Date_1Plus);
            long max;
            System.out.println("min = " + min);
            if(min < 0) {   // 오늘보다 예약 가능 시작 날짜가 더 앞서 있을 때
                max = min + 30;
                min = 0;
            } else
                max = min + 30;
            model.addAttribute("min", min);
            model.addAttribute("max", max);
            model.addAttribute("avgStar", avgStar);
            return "page/reservationpage2";
        } else {
            checkRes = 1;
            return "redirect:hospitalpage";
        }
    }

    @PostMapping("reservation")
    public String PostReservation(Model model, HttpServletRequest req, HttpServletResponse res,
                                  @RequestParam("Hid") int Hid, @RequestParam("time") int time,
                                  @RequestParam("Vid") int Vid, @RequestParam("Hname") String Hname,
                                  @RequestParam("date") String resDate) throws IOException, ParseException {
        System.out.println("Hid = " + Hid);
        System.out.println("time = " + time);
        System.out.println("resDate = " + resDate);
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo)user;
        int Uid = ((UserInfo) user).getUid();
        LocalDate today = LocalDate.now();

        //날짜 선택 안하면 다시
        LocalDate date = LocalDate.parse(resDate, DateTimeFormatter.ISO_DATE);
        System.out.println("date = " + date);
        LocalDate datePlus14 = date.plusMonths(1);
        System.out.println("datePlus14 = " + datePlus14);

        UserRsv userRsv = new UserRsv(userInfo.getUid(), Vid, Hid, Hid, Vid, date.toString(), time,
                datePlus14.toString(), time);
        System.out.println(userRsv.toString());

        if(userService.insertRsv(userRsv)) {   // 아무것도 없을 때
            hospitalService.reservation(Hid);   // 전체 예약 rest-2
            userInfo.setState(1);
            userService.updateUserState(userInfo);
            res.setContentType("text/html; charset=euc-kr");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('1차/2차 백신 예약 성공 : " + Hname + "');</script>");
            out.flush();
        }
        else {
            hospitalService.reservation2nd(Hid);   // 2차 예약 rest-1
            UserRsv OriginUserRsv = userService.findUserRsv(Uid);
            OriginUserRsv.setHid_2(Hid);
            OriginUserRsv.setHour_2(time);
            OriginUserRsv.setDate_2(resDate);
            OriginUserRsv.setVid_2(Vid);
            userService.updateUserRsv2nd(OriginUserRsv);
            res.setContentType("text/html; charset=euc-kr");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('2차 백신 예약 성공 : " + Hname + "');</script>");
            out.flush();
        }

        List<Hospital> hospitals = hospitalService.findAllHospitalByUid(Uid);
        model.addAttribute("hospitals", hospitals);
        model.addAttribute("user", userInfo.getUname());
        return "page/hospitalpage";
    }


    @GetMapping("news")
    public String GetNews(Model model) {
        return "page/news";
    }

    private class Date {
    }

    public String setLocation(int lid) {

        String location=null;

        if(lid==2)
            location="서울특별시";
        else if(lid==31)
            location="경기도";
        else if(lid==32)
            location="인천광역시";
        else if(lid==33)
            location="강원도";
        else if(lid==41)
            location="충청남도";
        else if(lid==42)
            location="대전광역시";
        else if(lid==43)
            location="충청북도";
        else if(lid==44)
            location="세종특별자치시";
        else if(lid==51)
            location="부산광역시";
        else if(lid==52)
            location="울산광역시";
        else if(lid==53)
            location="대구광역시";
        else if(lid==54)
            location="경상북도";
        else if(lid==55)
            location="경상남도";
        else if(lid==61)
            location="전라남도";
        else if(lid==62)
            location="광주광역시";
        else if(lid==63)
            location="전라북도";
        else if(lid==64)
            location="제주특별자치도";
        return location;
    }
}
