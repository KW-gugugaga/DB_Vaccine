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
        String s = user.toString();

        System.out.println("s = " + s);
        if(user == null) {
            System.out.println("NULL");
        } else {
            System.out.println("NOT NULL");
        }
        return "mainpage";
    }
}
