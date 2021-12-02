package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.vaccine.mapper.Mapper;

import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    Mapper mapper;

    public Integer findUserReviewByUidHid(Map<String, Integer> info) {
        return mapper.findUserReviewByUidHid(info);
    }
}
