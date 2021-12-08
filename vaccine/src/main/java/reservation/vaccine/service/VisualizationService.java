package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.vaccine.domain.Location;
import reservation.vaccine.mapper.Mapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
public class VisualizationService {
    @Autowired
    Mapper mapper;

    public List<Location> visualizeByLocation() {
        return mapper.visualizeByLocation();
    }
    public int findVaccinatedByAge(int ageGroup){return mapper.findVaccinatedByAge(ageGroup);}
    public int findAllByAge(int ageGroup){return mapper.findAllByAge(ageGroup);}
    public int findAllByStateDate2(int month){return mapper.findAllByStateDate2(month);}
    public List<HashMap<String, Object>> findVaccinatedByDay(String monthBefore) {return mapper.findVaccinatedByDay(monthBefore);};
}
