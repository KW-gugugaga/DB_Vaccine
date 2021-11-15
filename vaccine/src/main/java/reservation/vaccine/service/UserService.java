package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.mapper.UserMapper;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<UserInfo> findAll() {
        return userMapper.findAll();
    }
}
