package reservation.vaccine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("login")
    public String Login(Model model) {
        System.out.println("UserController.Login");
        return "user/login";
    }

    @GetMapping("myinfo")
    public String MyInfo(Model model) {
        System.out.println("UserController.MyInfo");
        model.addAttribute("id", "gdf4013");
        model.addAttribute("name", "Yerim");
        model.addAttribute("phone_num", "010-1111-1111");
        model.addAttribute("email", "gdf4013@naver.com");
        model.addAttribute("location", "seoul");
        model.addAttribute("age", 23);
        return "user/myinfo";
    }
}


