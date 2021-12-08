package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.vaccine.domain.Survey;
import reservation.vaccine.mapper.Mapper;

import java.util.List;

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

    public List<String> getSymptoms_1(int Vid_1, String day) {
        return null;
    }

    public List<String> getSymptoms_2(int Vid_2, String day) {
        return null;
    }
}
