package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<UserInfo> findAll() {
        return userMapper.findAll();
    }
    public UserInfo findUserById(String ID) { return userMapper.findUserById(ID); }
    public String findPWByUid(int Uid) { return userMapper.findPWByUid(Uid); }
    public String findUnameByUid(int Uid) { return userMapper.findUnameByUid(Uid); }
    public void insertUser(UserInfo userInfo) { userMapper.insertUser(userInfo); }
}
