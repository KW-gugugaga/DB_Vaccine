package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("Login")
    public String Login(Model model) {
        model.addAttribute("data", "hello yerim!");
        return "hello";
    }
}
