package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.vaccine.domain.Hospital;
import reservation.vaccine.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Service
public class HospitalService {

    @Autowired
    Mapper mapper;

    public List<Hospital> findAllHospitalByUid(int Uid){
        return mapper.findAllHospitalByUid(Uid);
    }
    public Hospital findHospitalByHid(int Hid) { return mapper.findHospitalByHid(Hid); }
    public String findHospitalNameByHid(int Hid) { return mapper.findHospitalNameByHid(Hid);}
    public void reservation(int Hid) { mapper.reservation(Hid);}
    public void reservation2nd(int Hid) { mapper.reservation2nd(Hid); }
    public void cancelBackAll(int Hid) { mapper.cancelBackAll(Hid);}
    public void cancelBackEach(int Hid) { mapper.cancelBackEach(Hid);}
}
