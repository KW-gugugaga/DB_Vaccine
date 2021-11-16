package reservation.vaccine.controller;

import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.tags.form.SelectTag;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("login")
    public String GetLogin(Model model) {
        System.out.println("UserController.GetLogin");
        return "user/login";
    }

    @PostMapping("login")
    public String PostLogin(Model model, @RequestParam("ID") String ID, @RequestParam("PW") String PW) {
        System.out.println("UserController.PostLogin");
        System.out.println("ID : " + ID + ", PW : " + PW);
        Integer Uid = userService.findUserById(ID);
        if(Uid == null) {
            System.out.println("아이디 존재하지 않음");
            //팝업
            return "user/login";
        }
        System.out.println("Uid = " + Uid);
        String getPW = userService.findPWByUid(Uid);
        System.out.println("getPW = " + getPW);
        if(PW.equals(getPW)) {
            System.out.println("비밀번호 일치");
            String Uname = userService.findUnameByUid(Uid);
            System.out.println("Uname = " + Uname);
            model.addAttribute("Uname", Uname);
            return "redirect:mainpage?Uid=" + Uid;
        } else {
            System.out.println("비밀번호 불일치");
            //팝업
            return "user/login";
        }
    }

    @GetMapping("join")
    public String GetJoin(Model model) {
        return "user/join";
    }

    @PostMapping("join")
    public String PostJoin(UserInfo userInfo) {
        System.out.println("UserController.PostJoin");
        System.out.println("To String : " + userInfo.toString());
        return "mainpage";
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
            System.out.println(userInfo.getUid() + " : " + userInfo.getUname());
        }
        return "mainpage";
    }
}


