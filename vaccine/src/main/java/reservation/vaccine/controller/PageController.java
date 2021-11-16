package reservation.vaccine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @GetMapping("mainpage")
    public String MainPage(Model model,  @RequestParam("Uid") int Uid) {
        System.out.println("PageController.MainPage");
        System.out.println("Uid = " + Uid);
        return "mainpage";
    }
}
