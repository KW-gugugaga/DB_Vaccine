package reservation.vaccine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reservation.vaccine.domain.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @GetMapping("mainpage")
    public String MainPage(Model model, HttpServletRequest req) {
        System.out.println("PageController.MainPage");
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

    @GetMapping("reservation")
    public String GetReservation(Model model) {
        return "page/reservation";
    }

    @GetMapping("news")
    public String GetNews(Model model) {
        return "page/news";
    }
}
