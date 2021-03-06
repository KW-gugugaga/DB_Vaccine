package reservation.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reservation.vaccine.domain.*;
import reservation.vaccine.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class PageController {

    private int checkRes = 0;
    Map<String, Double> map = new HashMap<>();
    int count = 0;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    VisualizationService visualizationService;

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("mainpage")
    public String GetMainPage(Model model, HttpServletRequest req) {
        System.out.println("PageController.GetMainPage");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo) user;
        String s = userInfo.toString();
        if (user == null) {
            return "user/login";
        } else {
            model.addAttribute("state", userInfo.getState());
            model.addAttribute("Uname", userInfo.getUname());
        }

        /*데이터 시각화*/
        //지역 별
        List<Location> locations = visualizationService.visualizeByLocation();
        Map<String, Float> locationData = new LinkedHashMap<String, Float>();
        for (Location location : locations) {
            locationData.put(location.getLname(), location.getRatio());
        }
        model.addAttribute("locationData", locationData);

        /*나이별 접종완료자*/
        ArrayList<Integer> ageVaccinated = new ArrayList<Integer>();
        ArrayList<Integer> ageAll = new ArrayList<Integer>();
        Map<String, Float> AgeData = new LinkedHashMap<String, Float>();

        for (int i = 8; i >= 1; i--) {
            ageVaccinated.add(visualizationService.findVaccinatedByAge(i));
            ageAll.add(visualizationService.findAllByAge(i));
        }

        for (int i = 0; i < 8; i++) {
            if (ageAll.get(i) != 0) {
                float ratio = (float) (ageVaccinated.get(i)) / (ageAll.get(i));
                if (i == 0)
                    AgeData.put("80대 이상", ratio);
                else if (i == 7)
                    AgeData.put("20대 미만", ratio);
                else
                    AgeData.put((8 - i) + "0대", ratio);
            } else
                AgeData.put((8 - i) + "0대", 0.0f);
        }

        for (String key : AgeData.keySet()) {
            float value = (float) AgeData.get(key);
        }

        model.addAttribute("AgeData", AgeData);

        /*날짜별 접종완료자*/
        LocalDate today = LocalDate.now();
        LocalDate monthBefore = today.minusMonths(1);
        String monthBeforeStr = monthBefore.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); //오늘로부터 한달 전 날짜 문자열
        System.out.println("monthBefore = " + monthBefore);

        int days = (int)monthBefore.until(today,ChronoUnit.DAYS);

        //1차
        int Vaccinated1stMonthAgo = visualizationService.findPrevious1stVaccinatedByDay(monthBeforeStr); //한달 전보다 이전 누적 접종완료자 수
        System.out.println("Vaccinated1stMonthAgo = " + Vaccinated1stMonthAgo);

        ArrayList<Integer> day1stVaccinated = new ArrayList<Integer>();
        ArrayList<String> dayMonth = new ArrayList<String>();
        Map<String, Integer> Day1stData = new LinkedHashMap<String, Integer>();
        LocalDate tempDate=monthBefore;
        String tempStr=monthBeforeStr;
        for(int i=0;i<days;i++)
        {
            day1stVaccinated.add(visualizationService.find1stVaccinatedByDay(tempStr));
            dayMonth.add(tempStr);
            tempDate=tempDate.plusDays(1);
            tempStr = tempDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        }
        System.out.println("day1stVaccinated = " + day1stVaccinated);

        int firstcount = visualizationService.findPrevious1stVaccinatedByDay(monthBeforeStr);
        for (int i = 0; i < days; i++) {
            firstcount += day1stVaccinated.get(i);
            Day1stData.put(dayMonth.get(i), firstcount);
        }
        System.out.println("Day1stData = " + Day1stData);

        model.addAttribute("Day1stData", Day1stData);

        //완료자
        int VaccinatedMonthAgo = visualizationService.findPreviousVaccinatedByDay(monthBeforeStr); //한달 전보다 이전 누적 접종완료자 수
        System.out.println("VaccinatedMonthAgo = " + VaccinatedMonthAgo);

        List<HashMap<String, Object>> dateVaccinated = visualizationService.findVaccinatedByDay(monthBeforeStr); //한달 내의 접종완료자 수
        for(int i = 0; i<dateVaccinated.size(); i++)
        {
            System.out.println("dateVaccinated= "+ dateVaccinated.get(i));
        }
        Map<String, Object> ExistDay = new HashMap<String, Object>();

        for(int i = 0; i<dateVaccinated.size(); i++) {
            ExistDay.put(dateVaccinated.get(i).get("date_2").toString(),dateVaccinated.get(i).get("count(*)"));
        }

        Map<String, Integer> DayData = new LinkedHashMap<String, Integer>();

        for(int i = 0; i<days;i++) {
            DayData.put(monthBeforeStr,0);
            monthBefore=monthBefore.plusDays(1);
            monthBeforeStr = monthBefore.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        int vaccinatedCount =0; //누적 완료자수 count
        vaccinatedCount = VaccinatedMonthAgo;
        for(String allDay : DayData.keySet()) {
            for(String existDay : ExistDay.keySet()) {
                if(allDay.equals(existDay)) {
                    vaccinatedCount += Integer.parseInt(ExistDay.get(existDay).toString()); //누적
                }
            }
            DayData.put(allDay,vaccinatedCount);
        }

        model.addAttribute("DayData", DayData);


        /*월별 접종완료자*/
        Month monthStr = today.getMonth();
        int current_month = monthStr.getValue();

        int start_month = current_month - 5;

        ArrayList<Integer> monthVaccinated = new ArrayList<Integer>();
        Map<String, Integer> MonthData = new LinkedHashMap<String, Integer>();
        Map<String, Integer> AccumulatedData = new LinkedHashMap<String, Integer>();
        for (int i = start_month; i <= current_month; i++) {
            monthVaccinated.add(visualizationService.findAllByStateDate2(i));
        }

        String sixmonthBefore = today.getYear()+"-"+start_month+"-"+1;
        System.out.println("sixmonthBefore = " + sixmonthBefore);

        int accumulated = visualizationService.findAllPastByStateDate2(sixmonthBefore);
        System.out.println("accumulated = " + accumulated);
        for (int i = 0; i < 6; i++) {
            accumulated += monthVaccinated.get(i);
            MonthData.put((i + start_month) + "월", (monthVaccinated.get(i)));
            AccumulatedData.put((i + start_month) + "월", accumulated);
        }
        model.addAttribute("MonthData", MonthData);
        model.addAttribute("AccumulatedData", AccumulatedData);

        return "page/mainpage";
    }

    @GetMapping("hospitalpage")
    public String GetHospitalPage(Model model, HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println("PageController.GetHospitalPage");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo) user;
        int Uid = ((UserInfo) user).getUid();
        List<Hospital> hospitals = hospitalService.findAllHospitalByUid(Uid);
        for (Hospital hospital : hospitals) {
            Float avgStar = reviewService.getAvgStar(hospital.getHid());
            if (avgStar == null) {
                hospital.setAvgStar("-");
            } else {
                hospital.setAvgStar(Float.toString((float) (Math.round(avgStar * 10) / 10.0)));
            }
        }
        String location = setLocation(userInfo.getLid());
        location += " ";
        String full_loc = location.concat(userInfo.getRest_addr());

        model.addAttribute("hospitals", hospitals);
        model.addAttribute("user", userInfo.getUname());
        model.addAttribute("full_loc", full_loc);

        if (checkRes == 1) {
            res.setContentType("text/html; charset=euc-kr");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('예약 불가 : 회원님의 예약 내역이 이미 존재합니다.');window.location.href ='hospitalpage'</script>");
            out.flush();
            checkRes = 0;
            return "";
        } else
            return "page/hospitalpage";
    }

    @PostMapping("reservationpage")
    public String PostReservationPage(Model model, HttpServletRequest req, HttpServletResponse res,
                                      @RequestParam("Hid") int Hid) throws IOException {
        System.out.println("PageController.PostReservationPage");
        Hospital hospital = hospitalService.findHospitalByHid(Hid);
        model.addAttribute("hospital", hospital);
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo) user;
        UserRsv userRsv = userService.findUserRsv(userInfo.getUid());

        int state = 0;
        if (userRsv != null) {
            if (userRsv.getDate_2() == null) {   // 1차만 있고 2차만 없을 때
                state = 1;
            }
        }

        if (userRsv == null) {   // 1차 2차 둘다 없을 때
            Float avg = reviewService.getAvgStar(Hid);
            String avgStar = null;
            List<String> reviews = null;
            if (avg == null) {
                avgStar = "-";
            } else {
                avg = (float) (Math.round(avg * 10) / 10.0);
                avgStar = Float.toString(avg);
                reviews = reviewService.findALlReviewByHid(Hid);
            }

            model.addAttribute("avgStar", avgStar);
            model.addAttribute("reviews", reviews);
            return "page/reservationpage";
        } else if (state == 1) {   // 1차만 있을 때
            String date_1 = userRsv.getDate_1();
            LocalDate Date_1 = LocalDate.parse(date_1, DateTimeFormatter.ISO_DATE);
            LocalDate Date_1Plus = Date_1.plusMonths(1);
            LocalDate today = LocalDate.now();
            long min = ChronoUnit.DAYS.between(today, Date_1Plus);
            long max;
            if (min < 0) {   // 오늘보다 예약 가능 시작 날짜가 더 앞서 있을 때
                max = min + 30;
                min = 0;
            } else
                max = min + 30;
            model.addAttribute("min", min);
            model.addAttribute("max", max);

            Float avg = reviewService.getAvgStar(Hid);
            String avgStar = null;
            List<String> reviews = null;
            if (avg == null) {
                avgStar = "-";
            } else {
                avg = (float) (Math.round(avg * 10) / 10.0);
                avgStar = Float.toString(avg);
                reviews = reviewService.findALlReviewByHid(Hid);
            }
            model.addAttribute("avgStar", avgStar);
            model.addAttribute("reviews", reviews);
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
        System.out.println("PageController.PostReservation");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo) user;
        int Uid = ((UserInfo) user).getUid();
        LocalDate today = LocalDate.now();

        //날짜 선택 안하면 다시
        LocalDate date = LocalDate.parse(resDate, DateTimeFormatter.ISO_DATE);
        LocalDate datePlus14 = date.plusMonths(1);

        UserRsv userRsv = new UserRsv(userInfo.getUid(), Vid, Hid, Hid, Vid, date.toString(), time,
                datePlus14.toString(), time);

        if (userService.insertRsv(userRsv)) {   // 아무것도 없을 때
            hospitalService.reservation(Hid);   // 전체 예약 rest-2
            userInfo.setState(1);
            userService.updateUserState(userInfo);
        } else {
            hospitalService.reservation2nd(Hid);   // 2차 예약 rest-1
            UserRsv OriginUserRsv = userService.findUserRsv(Uid);
            OriginUserRsv.setHid_2(Hid);
            OriginUserRsv.setHour_2(time);
            OriginUserRsv.setDate_2(resDate);
            OriginUserRsv.setVid_2(Vid);
            userService.updateUserRsv2nd(OriginUserRsv);
        }
        return "redirect:hospitalpage";
    }

    @GetMapping("news")
    public String GetNews(Model model) throws IOException {
        List<KoreaStats> koreaStatsList = coronaVirusDataService.getKoreaCovidDatas();
        model.addAttribute("koreaStats", koreaStatsList);
        return "page/news";
    }

    @GetMapping("news2")
    public String GetNews2(Model model) throws IOException {
        List<KoreaStats> koreaStatsList = coronaVirusDataService.getKoreaCovidDatas();
        model.addAttribute("koreaStats", koreaStatsList);
        return "page/news2";
    }

    @PostMapping("distance")
    @ResponseBody
    public int PostDistance(@RequestParam("oMap") Map<String, List<String>> map) {
        for(String key : map.keySet()) {
            List<String> value = map.get(key);
            for(String v : value) {
                System.out.println("v = " + v);
            }
        }
        return 1;
    }

    @GetMapping("guidepage")
    public String GetGuidePage(Model model) {
        return "page/guidepage";
    }

    public String setLocation(int lid) {
        String location = null;
        if (lid == 2)
            location = "서울특별시";
        else if (lid == 31)
            location = "경기도";
        else if (lid == 32)
            location = "인천광역시";
        else if (lid == 33)
            location = "강원도";
        else if (lid == 41)
            location = "충청남도";
        else if (lid == 42)
            location = "대전광역시";
        else if (lid == 43)
            location = "충청북도";
        else if (lid == 44)
            location = "세종특별자치시";
        else if (lid == 51)
            location = "부산광역시";
        else if (lid == 52)
            location = "울산광역시";
        else if (lid == 53)
            location = "대구광역시";
        else if (lid == 54)
            location = "경상북도";
        else if (lid == 55)
            location = "경상남도";
        else if (lid == 61)
            location = "전라남도";
        else if (lid == 62)
            location = "광주광역시";
        else if (lid == 63)
            location = "전라북도";
        else if (lid == 64)
            location = "제주특별자치도";
        return location;
    }
}