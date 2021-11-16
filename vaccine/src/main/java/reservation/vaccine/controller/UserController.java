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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String PostLogin(Model model, HttpServletRequest req,
                            @RequestParam("ID") String ID, @RequestParam("PW") String PW) {
        System.out.println("UserController.PostLogin");
        System.out.println("ID : " + ID + ", PW : " + PW);
        UserInfo userInfo = userService.findUserById(ID);
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("user", userInfo);
        if(userInfo == null) {
            System.out.println("아이디 또는 비밀번호 오류");
            //팝업
            return "user/login";
        } else {
            System.out.println("Uid = " + userInfo.getUid());
            return "redirect:mainpage";
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
        userService.insertUser(userInfo);
        //회원가입 성공 팝업
        return "user/login";
    }

    @GetMapping("myinfo")
    public String MyInfo(Model model,HttpServletRequest req) {
        System.out.println("UserController.MyInfo");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo) user;
        String name = userInfo.getUname();

        System.out.println("name = " + name);
        if (user == null) {
            System.out.println("NULL");
        }
        else
        {
            model.addAttribute("id", userInfo.getID());
            model.addAttribute("name", userInfo.getUname());
            model.addAttribute("phone_num", userInfo.getPhone_num());
            model.addAttribute("email", userInfo.getEmail());
            model.addAttribute("location", userInfo.getLid());
            model.addAttribute("ssn1", userInfo.getSsn1());
        }
        return "user/myinfo";
    }
    @GetMapping("findAll")
    public String findAll() {
        List<UserInfo> userInfos = userService.findAll();
        for (UserInfo userInfo : userInfos) {
            System.out.println(userInfo.getUid() + " : " + userInfo.getUname());
        }
        return "page/mainpage";
    }
}

