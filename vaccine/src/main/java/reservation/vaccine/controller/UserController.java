package reservation.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String PostLogin(Model model, HttpServletRequest req, HttpServletResponse res,
                            @RequestParam("ID") String ID, @RequestParam("PW") String PW) throws IOException {
        System.out.println("UserController.PostLogin");
        System.out.println("ID : " + ID + ", PW : " + PW);
        Map<String, String> loginInfo = new HashMap<String, String>();
        loginInfo.put("ID", ID);
        loginInfo.put("PW", PW);
        UserInfo userInfo = userService.findUserById(loginInfo);
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("user", userInfo);
        if(userInfo == null) {
            res.setContentType("text/html; charset=euc-kr");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('아이디 또는 비밀번호를 확인하십시오.');</script>");
            out.flush();
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
    public String PostJoin(UserInfo userInfo, HttpServletResponse res) throws IOException {
        System.out.println("UserController.PostJoin");
        System.out.println("To String : " + userInfo.toString());
        userService.insertUser(userInfo);
        res.setContentType("text/html; charset=euc-kr");
        PrintWriter out = res.getWriter();
        out.println("<script>alert('회원가입을 완료하였습니다.');</script>");
        out.flush();
        return "user/login";
    }

    @GetMapping("myinfo")
    public String MyInfo(Model model, HttpServletRequest req, HttpServletResponse res) {
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

