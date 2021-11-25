package reservation.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reservation.vaccine.domain.Hospital;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.domain.UserRsv;
import reservation.vaccine.service.HospitalService;
import reservation.vaccine.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class PageController {

    @Autowired
    HospitalService hospitalService;

    @Autowired
    UserService userService;

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
            model.addAttribute("Uname", userInfo.getUname());
            System.out.println("NOT NULL");
        }
        return "page/mainpage";
    }

    @GetMapping("hospitalpage")
    public String GetHospitalPage(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo)user;
        int Uid = ((UserInfo) user).getUid();
        List<Hospital> hospitals = hospitalService.findAllHospitalByUid(Uid);

        model.addAttribute("hospitals", hospitals);
        model.addAttribute("user", userInfo.getUname());
        return "page/hospitalpage";
    }

    @PostMapping("reservationpage")
    public String PostReservationPage(Model model, HttpServletRequest req, @RequestParam("Hid") int Hid) {
        System.out.println("Hid = " + Hid);
        Hospital hospital = hospitalService.findHospitalByHid(Hid);
        System.out.println(hospital.toString());
        model.addAttribute("hospital", hospital);

        // 내가 예약한 병원이면 예약 취소 버튼
        return "page/reservationpage";
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

        //날짜 선택 안하면 다시
        LocalDate date = LocalDate.parse(resDate, DateTimeFormatter.ISO_DATE);
        System.out.println("date = " + date);
        LocalDate datePlus14 = date.plusMonths(1);
        System.out.println("datePlus14 = " + datePlus14);

        UserRsv userRsv = new UserRsv(userInfo.getUid(), Vid, Hid, Hid, date.toString(), time,
                datePlus14.toString(), time);
        System.out.println(userRsv.toString());

        if(userService.insertRsv(userRsv)) {
            hospitalService.reservation(Hid);
            res.setContentType("text/html; charset=euc-kr");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('예약 성공 : " + Hname + "');</script>");
            out.flush();
        }
        else {
            System.out.println("이미 예약 내역 존재");
            res.setContentType("text/html; charset=euc-kr");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('예약 불가 : 회원님의 예약 내역이 이미 존재합니다.');</script>");
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
}
