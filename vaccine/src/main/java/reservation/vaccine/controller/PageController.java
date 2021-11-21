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
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

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
        for(Hospital hospital : hospitals) {
            System.out.println(hospital.toString());
        }
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
        // 내가 예약한 병원이면 예약취소 버튼 생성
        return "page/reservationpage";
    }

    @PostMapping("reservation")
    public String PostReservation(Model model, HttpServletRequest req,
                                  @RequestParam("Hid") int Hid, @RequestParam("time") int time,
                                  @RequestParam("Vid") int Vid) {
        System.out.println("Hid = " + Hid);
        System.out.println("time = " + time);
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo)user;
        int Uid = ((UserInfo) user).getUid();
        LocalDate date = LocalDate.now();
        System.out.println("date = " + date);
        UserRsv userRsv = new UserRsv(userInfo.getUid(), Vid, Hid, Hid, date.toString(), time,
                date.plusDays(14).toString(), time);
        System.out.println(userRsv.toString());

        //이미 예약했을 경우 예약 불가
        if(userService.insertRsv(userRsv))
            hospitalService.reservation(Hid);
        else
            System.out.println("이미 예약 존재");

        List<Hospital> hospitals = hospitalService.findAllHospitalByUid(Uid);
        model.addAttribute("hospitals", hospitals);
        model.addAttribute("user", userInfo.getUname());
        return "page/hospitalpage";
    }

    @GetMapping("news")
    public String GetNews(Model model) {
        return "page/news";
    }
}
