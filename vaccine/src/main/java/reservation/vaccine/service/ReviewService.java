package reservation.vaccine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.vaccine.domain.Review;
import reservation.vaccine.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    Mapper mapper;

    public Integer findUserReviewByUidHid(Map<String, Integer> info) {
        return mapper.findUserReviewByUidHid(info);
    }
    public void insertReview(Review review) {mapper.insertReview(review);};
    public Review findReview(Map<String, Integer> reviewInfo) {
        return mapper.findReview(reviewInfo);
    }
    public Float getAvgStar(int Hid) {
        return mapper.getAvgStar(Hid);
    }
    public List<String> findALlReviewByHid(int Hid) {
        return mapper.findALlReviewByHid(Hid);
    }
    public void deleteReview(int RVid) {
        mapper.deleteReview(RVid);
    }

}

