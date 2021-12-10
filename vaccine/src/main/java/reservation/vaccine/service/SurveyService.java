package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.vaccine.domain.Survey;
import reservation.vaccine.mapper.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<String> getSymptoms_day1_1(int Vid_1) {
        List<Integer> symptoms_day1_1 = mapper.getSymptoms_day1_1(Vid_1);
        List<String> symptoms_day1_1_str = new ArrayList<String>();
        for(Integer i : symptoms_day1_1) {
            symptoms_day1_1_str.add(mapper.getSymptom(i));
        }
        return symptoms_day1_1_str;
    }

    public List<String> getSymptoms_day3_1(int Vid_1) {
        List<Integer> symptoms_day1_1 = mapper.getSymptoms_day3_1(Vid_1);
        List<String> symptoms_day1_1_str = new ArrayList<String>();
        for(Integer i : symptoms_day1_1) {
            symptoms_day1_1_str.add(mapper.getSymptom(i));
        }
        return symptoms_day1_1_str;
    }

    public List<String> getSymptoms_day7_1(int Vid_1) {
        List<Integer> symptoms_day7_1 = mapper.getSymptoms_day7_1(Vid_1);
        List<String> symptoms_day7_1_str = new ArrayList<String>();
        for(Integer i : symptoms_day7_1) {
            symptoms_day7_1_str.add(mapper.getSymptom(i));
        }
        return symptoms_day7_1_str;
    }

    public List<String> getSymptoms_day1_2(int Vid_2) {
        List<Integer> symptoms_day1_2 = mapper.getSymptoms_day1_2(Vid_2);
        List<String> symptoms_day1_2_str = new ArrayList<String>();
        for(Integer i : symptoms_day1_2) {
            symptoms_day1_2_str.add(mapper.getSymptom(i));
        }
        return symptoms_day1_2_str;
    }

    public List<String> getSymptoms_day3_2(int Vid_2) {
        List<Integer> symptoms_day3_2 = mapper.getSymptoms_day3_2(Vid_2);
        List<String> symptoms_day3_2_str = new ArrayList<String>();
        for(Integer i : symptoms_day3_2) {
            symptoms_day3_2_str.add(mapper.getSymptom(i));
        }
        return symptoms_day3_2_str;
    }

    public List<String> getSymptoms_day7_2(int Vid_2) {
        List<Integer> symptoms_day7_2 = mapper.getSymptoms_day7_2(Vid_2);
        List<String> symptoms_day7_2_str = new ArrayList<String>();
        for(Integer i : symptoms_day7_2) {
            symptoms_day7_2_str.add(mapper.getSymptom(i));
        }
        return symptoms_day7_2_str;
    }

    public Integer findSurveyByUid(int Uid) {
        return mapper.findSurveyByUid(Uid);
    }
}
