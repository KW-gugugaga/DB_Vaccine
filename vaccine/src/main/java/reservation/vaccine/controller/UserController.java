package reservation.vaccine.controller;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.domain.UserRsv;
import reservation.vaccine.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        UserInfo userInfo = userService.findUserByIdPW(loginInfo);
        int state = 0;
        if(userInfo == null) {
            res.setContentType("text/html; charset=euc-kr");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('아이디 또는 비밀번호를 확인하십시오.');</script>");
            out.flush();
            return "user/login";
        } else {
            System.out.println("Uid = " + userInfo.getUid());
            UserRsv userRsv = userService.findUserRsv(userInfo.getUid());
            if(userRsv == null) {
                state = 0;   // 예약도 안하고 미접종
            } else {
                LocalDate today = LocalDate.now();
                String date_1 = userRsv.getDate_1();
                LocalDate Date_1 = LocalDate.parse(date_1, DateTimeFormatter.ISO_DATE);
                if(today.isBefore(Date_1)) {
                    state = 1;   // 예약했지만 미접종
                } else {   // 1차 맞음
                    state = 2;
                    if(today.isAfter(Date_1.plusDays(14))) {
                        state = 3;   // 1차 맞고 14일 지남
                        String date_2 = userRsv.getDate_2();
                        LocalDate Date_2 = LocalDate.parse(date_2, DateTimeFormatter.ISO_DATE);
                        if(today.isEqual(Date_2) || today.isAfter(Date_2)) {   // 2차 맞음
                            state = 4;
                            if(today.isAfter(Date_2.plusDays(14))) {
                                state = 5;   // 2차 후 14일 경과
                            }
                        }
                    }
                }
            }
            userInfo.setState(state);
            System.out.println(userInfo.toString());
            userService.updateUserState(userInfo);
            System.out.println("state = " + state);
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", userInfo);
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

        int lid=userInfo.getLid();

        String location=setLocation(lid);

        System.out.println("name = " + name);
        if (user == null) {
            System.out.println("NULL");
        }
        else
        {
            model.addAttribute("location", location);
            model.addAttribute("userinfo", userInfo);

        }
        return "user/myinfo";
    }

    //개인정보 수정
    @GetMapping("modify")
    public String GetModify(Model model,HttpServletRequest req) {
        System.out.println("UserController.Modify");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo) user;

        if (user == null) {
            System.out.println("NULL");
        }
        else
        {
            model.addAttribute("modifyUserinfo", userInfo);
        }
        return "user/modify";
    }

    @PostMapping("modify")
    public String PostModify(UserInfo userInfo, HttpServletRequest req, @RequestParam("ID") String ID) {

        System.out.println("UserController.PostModify");
        userInfo.setID(ID);
        userService.modifyUser(userInfo);
        userInfo = userService.findUserById(ID);

        System.out.println("To String : " + userInfo.toString());

        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("user", userInfo);
        return "redirect:mainpage";
    }

    @GetMapping("reservationinfo")
    public String GetReservationInfo(Model model, HttpServletRequest req) {
        System.out.println("UserController.GetReservationInfo");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo) user;
        UserRsv userRsv = userService.findUserRsv(userInfo.getUid());
        System.out.println("To String : " + userRsv.toString());

        if (userRsv == null) {
            model.addAttribute("noRsv", null);
        }
        else
        {
            model.addAttribute("userinfo",userInfo);
            model.addAttribute("userrsv", userRsv);
        }
        return "user/reservationinfo";
    }

    @GetMapping("findAll")
    public String findAll() {
        List<UserInfo> userInfos = userService.findAll();
        for (UserInfo userInfo : userInfos) {
            System.out.println(userInfo.getUid() + " : " + userInfo.getUname());
        }
        return "page/mainpage";
    }

    public String setLocation(int lid) {

        String location=null;

        if(lid==2)
            location="서울특별시";
        else if(lid==31)
            location="경기도";
        else if(lid==32)
            location="인천광역시";
        else if(lid==33)
            location="강원도";
        else if(lid==41)
            location="충청남도";
        else if(lid==42)
            location="대전광역시";
        else if(lid==43)
            location="충청북도";
        else if(lid==44)
            location="세종특별자치시";
        else if(lid==51)
            location="부산광역시";
        else if(lid==52)
            location="울산광역시";
        else if(lid==53)
            location="대구광역시";
        else if(lid==54)
            location="경상북도";
        else if(lid==55)
            location="경상남도";
        else if(lid==61)
            location="전라남도";
        else if(lid==62)
            location="광주광역시";
        else if(lid==63)
            location="전라북도";
        else if(lid==64)
            location="제주특별자치도";
        return location;
    }
}