package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.vaccine.domain.Survey;
import reservation.vaccine.mapper.Mapper;

@Service
public class SurveyService {

    @Autowired
    Mapper mapper;

    public void insertSurvey(Survey survey) {
        mapper.insertSurvey(survey);
    }

    public String findVaccineNameByVid(int Vid) {
        return mapper.findVaccineNameByVid(Vid);
    }
}
