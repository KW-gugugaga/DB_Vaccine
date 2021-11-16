package reservation.vaccine.mapper;

import reservation.vaccine.domain.UserInfo;

import java.util.HashMap;
import java.util.List;

public interface UserMapper {

    List<UserInfo> findAll();
    UserInfo findUserById(String ID);
    String findPWByUid(int Uid);
    String findUnameByUid(int Uid);
    void insertUser(UserInfo userInfo);
}
