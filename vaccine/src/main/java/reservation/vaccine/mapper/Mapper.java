package reservation.vaccine.mapper;

import reservation.vaccine.domain.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Mapper {

    //user
    List<UserInfo> findAll();
    UserInfo findUserByIdPw(Map<String, String> loginInfo);
    UserInfo findUserById(String ID);
    void insertUser(UserInfo userInfo);
    void modifyUser(UserInfo userInfo);
    void updateUserRsv2nd(UserRsv userRsv);

    void insertRsv(UserRsv userRsv);
    UserRsv findUserRsv(int Uid);
    UserInfo findUserInfoRsv(int Uid);

    void updateUserState(UserInfo userInfo);
    void cancelAll(int Uid);
    void cancelSecond(int Uid);

    int checkID(String ID);

    String findUserID(Map<String, String> inputInfo);
    String findUserPW(Map<String, String> inputInfo);

    //hospital
    List<Hospital> findAllHospitalByUid(int Uid);
    Hospital findHospitalByHid(int Hid);
    List<Hospital> findHospitalByHid(Map<String, Integer> Hids);
    void reservation(int Hid);
    void reservation2nd(int Hid);
    String findHospitalNameByHid(int Hid);
    void cancelBackAll(int Hid);
    void cancelBackEach(int Hid);
    int findHidByHname(String Hname);

    //visualization
    List<Location> visualizeByLocation();
    int findVaccinatedByAge(int ageGroup);
    int findAllByAge(int ageGroup);
    int findAllByStateDate2(int month);
    int findAllPastByStateDate2(String month);
    List<HashMap<String, Object>> findVaccinatedByDay(String monthBefore);
    int findPreviousVaccinatedByDay(String monthBefore);
    int find1stVaccinatedByDay(String monthBefore);
    int findPrevious1stVaccinatedByDay(String monthBefore);

    //review
    Integer findUserReviewByUidHid(Map<String, Integer> info);
    void insertReview(Review review);
    Review findReview(Map<String, Integer> reviewInfo);
    Float getAvgStar(int Hid);
    List<String> findALlReviewByHid(int Hid);
    void deleteReview(int RVid);

    //servey
    void insertSurvey(Survey survey);
    String findVaccineNameByVid(int Vid);
    List<Integer> getSymptoms_day1_1(int Vid_1);
    List<Integer> getSymptoms_day3_1(int Vid_1);
    List<Integer> getSymptoms_day7_1(int Vid_1);
    List<Integer> getSymptoms_day1_2(int Vid_2);
    List<Integer> getSymptoms_day3_2(int Vid_2);
    List<Integer> getSymptoms_day7_2(int Vid_2);
    String getSymptom(int Sid);
    Integer findSurveyByUid(int Uid);
}
