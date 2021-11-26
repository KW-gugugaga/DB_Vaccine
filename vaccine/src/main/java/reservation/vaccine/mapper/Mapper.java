package reservation.vaccine.mapper;

import reservation.vaccine.domain.Hospital;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.domain.UserRsv;

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

    //hospital
    List<Hospital> findAllHospitalByUid(int Uid);
    Hospital findHospitalByHid(int Hid);
    void reservation(int Hid);
    void reservation2nd(int Hid);

}
