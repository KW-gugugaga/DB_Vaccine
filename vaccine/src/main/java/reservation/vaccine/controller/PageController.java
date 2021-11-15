package reservation.vaccine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("mainpage")
    public String MainPage(Model model) {
        System.out.println("PageController.MainPage");
        model.addAttribute("name", "Yerim");
        return "mainpage";
    }
}
