package reservation.vaccine.mapper;

import reservation.vaccine.domain.UserInfo;

import java.util.HashMap;
import java.util.List;

public interface UserMapper {

    List<UserInfo> findAll();
    Integer findUserById(String ID);
    String findPWByUid(int Uid);
    String findUnameByUid(int Uid);
}
