package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.vaccine.domain.UserInfo;
import reservation.vaccine.domain.UserRsv;
import reservation.vaccine.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    Mapper mapper;

    public List<UserInfo> findAll() {
        return mapper.findAll();
    }
    public UserInfo findUserById(Map<String, String> login) { return mapper.findUserByIdPw(login); }
    public void insertUser(UserInfo userInfo) { mapper.insertUser(userInfo); }
    public boolean insertRsv(UserRsv userRsv) {
        if(mapper.findUserRsv(userRsv.getUid()) == null) {
            System.out.println("UserService.insertRsv null");
            mapper.insertRsv(userRsv);
            return true;
        } else
            return false;
    }
}
