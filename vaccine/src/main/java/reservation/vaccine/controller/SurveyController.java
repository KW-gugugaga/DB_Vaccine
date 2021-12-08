package reservation.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;
import reservation.vaccine.domain.Review;
import reservation.vaccine.domain.Survey;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.domain.UserRsv;
import reservation.vaccine.service.SurveyService;
import reservation.vaccine.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class SurveyController {

    @Autowired
    UserService userService;

    @Autowired
    SurveyService surveyService;

    @GetMapping("survey")
    public String GetSurvey(Model model, HttpServletRequest req) {
        System.out.println("SurveyController.GetSurvey");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo)user;
        int state = userInfo.getState();
        if(state == 5) {
            UserRsv userRsv = userService.findUserRsv(userInfo.getUid());
            String vac_1 = surveyService.findVaccineNameByVid(userRsv.getVid_1());
            String vac_2 = surveyService.findVaccineNameByVid(userRsv.getVid_2());
            System.out.println("vac_1 = " + vac_1);
            System.out.println("vac_2 = " + vac_2);
            model.addAttribute("vac_1", vac_1);
            model.addAttribute("vac_2", vac_2);
        }
        model.addAttribute("state", state);
        return "survey/survey";
    }

    @PostMapping("survey")
    public String PostSurvey(Model model, HttpServletRequest req,
                             @RequestParam("1_1") int day1_1, @RequestParam("1_3") int day3_1, @RequestParam("1_7") int day7_1,
                             @RequestParam("2_1") int day1_2, @RequestParam("2_3") int day3_2, @RequestParam("2_7") int day7_2) {
        System.out.println("SurveyController.PostSurvey");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo)user;
        UserRsv userRsv = userService.findUserRsv(userInfo.getUid());
        int vid_1 = userRsv.getVid_1();
        int vid_2 = userRsv.getVid_2();
        Survey survey = new Survey(userInfo.getUid(), vid_1, day1_1, day3_1, day7_1, vid_2, day1_2, day3_2, day7_2);
        System.out.println(survey.toString());
        surveyService.insertSurvey(survey);
        return "redirect:mainpage";
    }
}
