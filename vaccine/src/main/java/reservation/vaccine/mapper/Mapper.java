package reservation.vaccine.mapper;

import reservation.vaccine.domain.Hospital;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.domain.UserRsv;

import java.util.HashMap;
import java.util.List;

public interface Mapper {

    //user
    List<UserInfo> findAll();
    UserInfo findUserById(String ID);
    String findPWByUid(int Uid);
    String findUnameByUid(int Uid);
    void insertUser(UserInfo userInfo);
    void modifyUser(UserInfo userInfo);

    void insertRsv(UserRsv userRsv);
    Integer findUserRsv(int Uid);

    //hospital
    List<Hospital> findAllHospitalByUid(int Uid);
    Hospital findHospitalByHid(int Hid);
    void reservation(int Hid);

}
