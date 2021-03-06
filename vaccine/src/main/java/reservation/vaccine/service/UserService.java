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
    public UserInfo findUserByIdPW(Map<String, String> login) { return mapper.findUserByIdPw(login); }
    public UserInfo findUserById(String ID) { return mapper.findUserById(ID); }
    public void insertUser(UserInfo userInfo) { mapper.insertUser(userInfo); }
    public boolean insertRsv(UserRsv userRsv) {

        if(mapper.findUserRsv(userRsv.getUid()) == null) {
            System.out.println("UserService.insertRsv null");
            mapper.insertRsv(userRsv);
            return true;
        } else
            return false;
    }

    public void modifyUser(UserInfo userInfo) { mapper.modifyUser(userInfo); }
    public UserRsv findUserRsv(int Uid) { return mapper.findUserRsv(Uid); }
    public void updateUserState(UserInfo userInfo) { mapper.updateUserState(userInfo);}
    public void cancelAll(int Uid) {
        mapper.cancelAll(Uid);
    }

    public void cancelSecond(int Uid) {
        mapper.cancelSecond(Uid);
    }

    public void updateUserRsv2nd(UserRsv userRsv) { mapper.updateUserRsv2nd(userRsv);}

    public int checkID(String ID) {
        return mapper.checkID(ID);
    }

    public String findUserID(Map<String, String> inputInfo) {
        return mapper.findUserID(inputInfo);
    }
    public String findUserPW(Map<String, String> inputInfo) {
        return mapper.findUserPW(inputInfo);
    }

}
