package reservation.vaccine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("login")
    public String hello(Model model) {
        model.addAttribute("data", "Yelim");
        return "user/login";

    }
    @GetMapping("join")
    public String join(Model model) {
        return "user/join";
    }
}


