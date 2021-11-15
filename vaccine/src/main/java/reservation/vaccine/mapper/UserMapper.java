package reservation.vaccine.mapper;

import reservation.vaccine.domain.UserInfo;

import java.util.List;

public interface UserMapper {

    List<UserInfo> findAll();
}
