package reservation.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reservation.vaccine.domain.Hospital;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.domain.UserRsv;
import reservation.vaccine.domain.Vaccine;
import reservation.vaccine.service.HospitalService;
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

    @Autowired
    HospitalService hospitalService;

    @GetMapping("login")
    public String GetLogin(Model model) {
        System.out.println("UserController.GetLogin");
        return "user/login";
    }

    @PostMapping("login")
    public String PostLogin(Model model, HttpServletRequest req, HttpServletResponse res,
                            @RequestParam("ID") String ID, @RequestParam("PW") String PW) throws IOException {
        System.out.println("UserController.PostLogin");
        Map<String, String> loginInfo = new HashMap<String, String>();
        loginInfo.put("ID", ID);
        loginInfo.put("PW", PW);
        UserInfo userInfo = userService.findUserByIdPW(loginInfo);
        int state = 0;
        if (userInfo == null) {
            res.setContentType("text/html; charset=euc-kr");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('아이디 또는 비밀번호를 확인하십시오.');</script>");
            out.flush();
            return "user/login";
        } else {
            UserRsv userRsv = userService.findUserRsv(userInfo.getUid());
            if (userRsv == null) {
                state = 0;   // 예약도 안하고 미접종
            } else {
                LocalDate today = LocalDate.now();
                String date_1 = userRsv.getDate_1();
                LocalDate Date_1 = LocalDate.parse(date_1, DateTimeFormatter.ISO_DATE);
                if (today.isBefore(Date_1)) {
                    state = 1;   // 예약했지만 미접종
                } else {   // 1차 맞음
                    state = 2;
                    if (today.isAfter(Date_1.plusDays(14))) {
                        state = 3;   // 1차 맞고 14일 지남
                        String date_2 = userRsv.getDate_2();
                        if (date_2 != null) {
                            LocalDate Date_2 = LocalDate.parse(date_2, DateTimeFormatter.ISO_DATE);
                            if (today.isEqual(Date_2) || today.isAfter(Date_2)) {   // 2차 맞음
                                state = 4;
                                if (today.isAfter(Date_2.plusDays(14))) {
                                    state = 5;   // 2차 후 14일 경과
                                }
                            }
                        }
                    }
                }
            }
            userInfo.setState(state);
            userService.updateUserState(userInfo);
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
    public String PostJoin(UserInfo userInfo) throws IOException {
        System.out.println("UserController.PostJoin");
        userService.insertUser(userInfo);
        return "redirect:login";
    }

    @PostMapping("idCheck")
    @ResponseBody
    public int PostIdCheck(@RequestParam("ID") String ID) {
        System.out.println("UserController.PostIdCheck");
        int count = userService.checkID(ID);
        return count;
    }

    @GetMapping("myinfo")
    public String GetMyInfo(Model model, HttpServletRequest req, HttpServletResponse res) {
        System.out.println("UserController.GetMyInfo");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo) user;
        String name = userInfo.getUname();

        int lid = userInfo.getLid();
        String location = setLocation(lid);

        model.addAttribute("location", location);
        model.addAttribute("userinfo", userInfo);

        return "user/myinfo";
    }

    //개인정보 수정
    @GetMapping("modify")
    public String GetModify(Model model, HttpServletRequest req) {
        System.out.println("UserController.Modify");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo) user;

        model.addAttribute("modifyUserinfo", userInfo);

        return "user/modify";
    }

    @PostMapping("modify")
    public String PostModify(UserInfo userInfo, HttpServletRequest req, @RequestParam("ID") String ID) {
        System.out.println("UserController.PostModify");
        userInfo.setID(ID);
        userService.modifyUser(userInfo);
        userInfo = userService.findUserById(ID);

        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("user", userInfo);
        return "redirect:mainpage";
    }

    @GetMapping("reservationinfo")
    public String ReservationInfo(Model model, HttpServletRequest req) {
        System.out.println("UserController.ReservationInfo");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo) user;
        UserRsv userRsv = userService.findUserRsv(userInfo.getUid());
        String Vname1 = null, Vname2 = null;


        int state = userInfo.getState();

        if (userRsv == null) { //미접종, 예약 X
            model.addAttribute("rsvstate", 0);
        } else { //에약내역이 뭐라도 있음
            /*백신 이름 설정*/
            Vname1=getVaccineName(userRsv.getVid_1());
            Vname2=getVaccineName(userRsv.getVid_2());

            int rsvState = 0;
            String Hname1 = hospitalService.findHospitalNameByHid(userRsv.getHid_1()); //1차 병원이름
            String Hname2 = null;

            if (state == 1 && (userRsv.getDate_2() != null)) //미접종, 예약완료 -> 전체취소
            {
                Hname2 = hospitalService.findHospitalNameByHid(userRsv.getHid_2()); //2차 병원이름
                rsvState = 1;
            } else if (userRsv.getDate_2() == null) //2차 예약 X
            {
                if (state == 1) //미접종, 1차 예약 O
                    rsvState = 2;
                else if (state == 2 || state == 3)
                    rsvState = 3; //1차 접종 후, 1차 예약 O
            } else if (userRsv.getDate_2() != null) //2차 예약 O
            {
                Hname2 = hospitalService.findHospitalNameByHid(userRsv.getHid_2()); //2차 병원이름
                if (state == 2 || state == 3) //2차 접종 전
                    rsvState = 4;
                else if (state == 4 || state == 5) // //2차 접종 후
                    rsvState = 5;
            }

            model.addAttribute("rsvstate", rsvState); //예약 상태
            model.addAttribute("userinfo", userInfo);
            model.addAttribute("userrsv", userRsv);
            model.addAttribute("state", state);
            model.addAttribute("vname1", Vname1);
            model.addAttribute("vname2", Vname2);
            model.addAttribute("hname1", Hname1);
            model.addAttribute("hname2", Hname2);
        }

        return "user/reservationinfo";
    }


    @GetMapping("cancel")
    public String GetCancel(Model model, HttpServletRequest req, @RequestParam("what") String what) {
        System.out.println("UserController.GetCancel");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo) user;
        UserRsv userRsv = userService.findUserRsv(userInfo.getUid());

        String Hname1 = null;
        String Hname2 = null;

        if(what.equals("전체")) {
            Hname1=hospitalService.findHospitalNameByHid(userRsv.getHid_1());
            Hname2=hospitalService.findHospitalNameByHid(userRsv.getHid_2());
        }
        else if(what.equals("1차")) {
            Hname1=hospitalService.findHospitalNameByHid(userRsv.getHid_1());
        }
        else if(what.equals("2차")) {
            Hname2=hospitalService.findHospitalNameByHid(userRsv.getHid_2());
        }

        String Vname1 = getVaccineName(userRsv.getVid_1());
        String Vname2 = getVaccineName(userRsv.getVid_2());

        model.addAttribute("userrsv", userRsv);
        model.addAttribute("what", what);
        model.addAttribute("vname1", Vname1);
        model.addAttribute("vname2", Vname2);
        model.addAttribute("hname1", Hname1);
        model.addAttribute("hname2", Hname2);

        return "user/cancel";
    }

    @PostMapping("cancel")
    public String PostCancel(@RequestParam("Uid") int Uid, @RequestParam("what") String what) {
        System.out.println("UserController.PostCancel");
        UserRsv userRsv = userService.findUserRsv(Uid);

        if (what.equals("전체")) {
            userService.cancelAll(Uid);
            if (userRsv.getHid_1() == userRsv.getHid_2()) {
                hospitalService.cancelBackAll(userRsv.getHid_1());
            } else {
                hospitalService.cancelBackEach(userRsv.getHid_1());
                hospitalService.cancelBackEach(userRsv.getHid_2());
            }
        } else if (what.equals("1차")) {
            userService.cancelAll(Uid);
            hospitalService.cancelBackEach(userRsv.getHid_1());
        } else if (what.equals("2차")) {
            userService.cancelSecond(Uid);
            hospitalService.cancelBackEach(userRsv.getHid_2());
        }
        return "redirect:mainpage";
    }

    public String setLocation(int lid) {

        String location = null;

        if (lid == 2)
            location = "서울특별시";
        else if (lid == 31)
            location = "경기도";
        else if (lid == 32)
            location = "인천광역시";
        else if (lid == 33)
            location = "강원도";
        else if (lid == 41)
            location = "충청남도";
        else if (lid == 42)
            location = "대전광역시";
        else if (lid == 43)
            location = "충청북도";
        else if (lid == 44)
            location = "세종특별자치시";
        else if (lid == 51)
            location = "부산광역시";
        else if (lid == 52)
            location = "울산광역시";
        else if (lid == 53)
            location = "대구광역시";
        else if (lid == 54)
            location = "경상북도";
        else if (lid == 55)
            location = "경상남도";
        else if (lid == 61)
            location = "전라남도";
        else if (lid == 62)
            location = "광주광역시";
        else if (lid == 63)
            location = "전라북도";
        else if (lid == 64)
            location = "제주특별자치도";
        return location;
    }

    public String getVaccineName(int vid){
        String vaccine=null;

        if (vid == 0)
            vaccine = "화이자";
        else if (vid == 1)
            vaccine = "모더나";
        else if (vid == 2)
            vaccine = "아스트라제네카";
        return vaccine;
    }
    @GetMapping("findID")
    public String GetFindID(HttpServletRequest req) {
        return "user/findid";
    }

    @PostMapping("findID")
    public String PostFindID(Model model, HttpServletRequest req,
                             @RequestParam("Uname") String Uname, @RequestParam("Email") String Email) {
        System.out.println("UserController.findID");
        Map<String, String> inputInfo = new HashMap<String, String>();
        inputInfo.put("Uname", Uname);
        inputInfo.put("Email", Email);
        String userID = userService.findUserID(inputInfo);
        if (userID == null) {
            model.addAttribute("state", 0);
        } else {
            model.addAttribute("state", 1);
            model.addAttribute("userID", userID);
        }
        return "user/getid";
    }

    @GetMapping("findPW")
    public String GetFindPW(HttpServletRequest req) {
        return "user/findpw";
    }

    @PostMapping("findPW")
    public String PostFindPW(Model model, HttpServletRequest req,
                             @RequestParam("Uname") String Uname, @RequestParam("ID") String ID) {
        System.out.println("UserController.PostFindPW");
        Map<String, String> inputInfo = new HashMap<String, String>();
        inputInfo.put("Uname", Uname);
        inputInfo.put("ID", ID);

        String userPW = userService.findUserPW(inputInfo);
        if (userPW == null) {
            model.addAttribute("state", 0);
        } else {
            model.addAttribute("state", 1);
            model.addAttribute("userPW", userPW);
        }
        return "user/getpw";
    }

    @GetMapping("logout")
    public String GetLogout(HttpServletRequest req) {
        req.getSession().invalidate();   // 로그아웃
        return "redirect:login";
    }
}