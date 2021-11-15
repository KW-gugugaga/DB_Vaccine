package reservation.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("login")
    public String Login(Model model) {
        System.out.println("UserController.Login");
        return "user/login";

    }
    @GetMapping("join")
    public String join(Model model) {
        return "user/join";
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

    @GetMapping("findAll")
    public String findAll() {
        List<UserInfo> userInfos = userService.findAll();
        for(UserInfo userInfo : userInfos) {
            System.out.println(userInfo.getUname());
        }
        return "mainpage";
    }
}


