package reservation.vaccine.mapper;

import reservation.vaccine.domain.*;

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

    //visualization
    List<Location> visualizeByLocation();
    int findVaccinatedByAge(int ageGroup);
    int findAllByAge(int ageGroup);
    int findAllByStateDate2(int month);

    //review
    Integer findUserReviewByUidHid(Map<String, Integer> info);
    void insertReview(Review review);
    Review findReview(Map<String, Integer> reviewInfo);
    Float getAvgStar(int Hid);
    List<String> findALlReviewByHid(int Hid);

}
