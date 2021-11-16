package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import reservation.vaccine.mapper.HospitalMapper;

public class HospitalService {

    @Autowired
    HospitalMapper hospitalMapper;
}
