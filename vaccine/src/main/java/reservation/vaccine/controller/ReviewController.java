package reservation.vaccine.controller;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reservation.vaccine.domain.Hospital;
import reservation.vaccine.domain.Review;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.domain.UserRsv;
import reservation.vaccine.service.HospitalService;
import reservation.vaccine.service.ReviewService;
import reservation.vaccine.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ReviewController {

    @Autowired
    UserService userService;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    ReviewService reviewService;

    @GetMapping("review")
    public String GetReview(Model model, HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println("PageController.GetReview");
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo)user;
        int uid = userInfo.getUid();
        UserRsv userRsv = userService.findUserRsv(userInfo.getUid());
        if(userRsv == null) {   // 아직 예약 내역이 없을 때
            model.addAttribute("userState", 0);
        } else if(userRsv.getDate_2() == null) {   // 아직 1차만 예약했을 때
            String date_1 = userRsv.getDate_1();
            LocalDate Date_1 = LocalDate.parse(date_1, DateTimeFormatter.ISO_DATE);
            LocalDate today = LocalDate.now();
            if(today.isBefore(Date_1)) {   // 1차를 안맞은 경우
                model.addAttribute("userState", "1X");
            } else {   // 1차를 맞은 경우
                model.addAttribute("userState", "1O");
                int hid = userRsv.getHid_1();
                Map<String, Integer> info = new HashMap<String, Integer>();
                info.put("Uid", uid);
                info.put("Hid", hid);
                if(reviewService.findUserReviewByUidHid(info) == null) {   // 아직 남긴 리뷰가 없을 때
                    model.addAttribute("reviewState", "X");
                } else {   // 남긴 리뷰가 있을 때
                    model.addAttribute("reviewState", "O");
                }
            }
            Hospital hospital = hospitalService.findHospitalByHid(userRsv.getHid_1());
            model.addAttribute("hospital", hospital);
            model.addAttribute("userRsv", userRsv);
        } else {   // 1차, 2차 둘다 있을 때
            String date_1 = userRsv.getDate_1();
            LocalDate Date_1 = LocalDate.parse(date_1, DateTimeFormatter.ISO_DATE);
            String date_2 = userRsv.getDate_2();
            LocalDate Date_2 = LocalDate.parse(date_2, DateTimeFormatter.ISO_DATE);
            LocalDate today = LocalDate.now();
            int hid1 = userRsv.getHid_1();
            Map<String, Integer> info1 = new HashMap<String, Integer>();
            info1.put("Uid", uid);
            info1.put("Hid", hid1);
            if(userRsv.getHid_1() == userRsv.getHid_2()) {   // 1차 2차 병원 같을 때
                model.addAttribute("hospitalState", "same");
                if(today.isBefore(Date_1)) {   // 아직 백신 안맞은 경우
                    model.addAttribute("userState", "2XX");
                } else if(today.isAfter(Date_1) && today.isBefore(Date_2)) {   // 1차만 맞은 경우
                    model.addAttribute("userState", "2OX");
                    if(reviewService.findUserReviewByUidHid(info1) == null) {   // 남긴 리뷰가 없을 때
                        model.addAttribute("reviewState", "X");
                    } else {   // 남긴 리뷰가 있을 때
                        model.addAttribute("reviewState", "O");
                    }
                } else {   // 1차 2차 둘다 맞은 경우
                    model.addAttribute("userState", "2OO");
                    if(reviewService.findUserReviewByUidHid(info1) == null) {   // 남긴 리뷰가 없을 때
                        model.addAttribute("reviewState", "X");
                    } else {
                        model.addAttribute("reviewState", "O");
                    }
                }
            } else {   // 1차 2차 병원 다를 때
                int hid2 = userRsv.getHid_2();
                Map<String, Integer> info2 = new HashMap<String, Integer>();
                info2.put("Uid", uid);
                info2.put("Hid", hid2);
                model.addAttribute("hospitalState", "diff");
                if(today.isBefore(Date_1)) {   // 아직 백신 안맞은 경우
                    model.addAttribute("userState", "2XX");
                } else if(today.isAfter(Date_1) && today.isBefore(Date_2)) {   // 1차만 맞은 경우
                    model.addAttribute("userState", "2OX");
                    if(reviewService.findUserReviewByUidHid(info1) == null) {   // 남긴 리뷰가 없을 때
                        model.addAttribute("reviewState1", "X");
                    } else {   // 남긴 리뷰가 있을 때
                        model.addAttribute("reviewState1", "O");
                    }
                } else {   // 1차 2차 둘다 맞은 경우
                    model.addAttribute("userState", "2OO");
                    if(reviewService.findUserReviewByUidHid(info1) == null) {   // 1차 병원 남긴 리뷰가 없을 때
                        model.addAttribute("reviewState1", "X");
                    } else {
                        model.addAttribute("reviewState1", "O");
                    }

                    if(reviewService.findUserReviewByUidHid(info2) == null) {   // 2차 병원 남긴 리뷰가 없을 때
                        model.addAttribute("reviewState2", "X");
                    } else {
                        model.addAttribute("reviewState2", "O");
                    }
                }
            }
            Hospital hospital1 = hospitalService.findHospitalByHid(userRsv.getHid_1());
            Hospital hospital2 = hospitalService.findHospitalByHid(userRsv.getHid_2());
            model.addAttribute("hospital1", hospital1);
            model.addAttribute("hospital2", hospital2);
            model.addAttribute("userRsv", userRsv);
        }

        return "page/review";
    }

    @GetMapping("writeReview")
    public String GetWriteReview(Model model, HttpServletRequest req, @RequestParam("Hid") int Hid) {
        System.out.println("ReviewController.GetWriteReview");
        System.out.println("Hid = " + Hid);
        String Hname = hospitalService.findHospitalNameByHid(Hid);
        model.addAttribute("Hname", Hname);
        model.addAttribute("Hid", Hid);
        return "review/writereview";
    }

    @PostMapping("writeReview")
    public void PostWriteReview(Model model, HttpServletRequest req, HttpServletResponse res,
                                @RequestParam("rating") int rating, @RequestParam("Hname") String Hname,
                                @RequestParam("Hid") int Hid, @RequestParam("review") String reviewText) throws IOException {
        System.out.println("ReviewController.PostWriteReview");
        System.out.println("rating = " + rating);
        System.out.println("Hid = " + Hid);
        System.out.println("reviewText = " + reviewText);
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo)user;
        Review review = new Review(userInfo.getUid(), Hid, rating, reviewText);
        reviewService.insertReview(review);
        res.setContentType("text/html; charset=euc-kr");
        PrintWriter out = res.getWriter();
        String scriptString = "<script>alert('리뷰가 등록되었습니다.(" + Hname + ")');window.close();</script>";
        out.println(scriptString);
        out.flush();
        return;
    }

    @GetMapping("viewReview")
    public String GetViewReview(Model model, HttpServletRequest req, @RequestParam("Hid") int Hid) {
        System.out.println("ReviewController.GetViewReview");
        System.out.println("Hid = " + Hid);
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        UserInfo userInfo = (UserInfo)user;
        Map<String, Integer> reviewInfo = new HashMap<String, Integer>();
        reviewInfo.put("Uid", userInfo.getUid());
        reviewInfo.put("Hid", Hid);
        Review review = reviewService.findReview(reviewInfo);
        System.out.println(review.toString());
        model.addAttribute("review", review);
        if(review.getStar() == 1) {
            return "review/viewreview1";
        } else if(review.getStar() == 2) {
            return "review/viewreview2";
        } else if(review.getStar() == 3) {
            return "review/viewreview3";
        } else if(review.getStar() == 4) {
            return "review/viewreview4";
        } else {
            return "review/viewreview5";
        }
    }
}
