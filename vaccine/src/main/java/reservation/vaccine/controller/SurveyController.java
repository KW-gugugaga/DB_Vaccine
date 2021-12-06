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
public class SurveyController {
    @GetMapping("survey")
    public String GetSurvey(Model model) {
        System.out.println("SurveyController.GetSurvey");
        return "survey/survey";
    }
}
