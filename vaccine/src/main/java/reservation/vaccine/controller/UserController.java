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
    public String hello(Model model) {
        model.addAttribute("data", "Yelim");
        return "user/login";
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


