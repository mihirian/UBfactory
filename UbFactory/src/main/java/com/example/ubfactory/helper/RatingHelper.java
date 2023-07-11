package com.example.ubfactory.helper;

import com.example.ubfactory.entities.Rating;
import com.example.ubfactory.repository.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class RatingHelper {
    public static final Logger logger = LoggerFactory.getLogger(RatingHelper.class);
    @Autowired
    private RatingRepository ratingRepository;
    public double calculateAverageRating(Integer productId) {
        List<Rating> rating = ratingRepository.findByProductId(productId);
        int totalPoints = 0;
        int totalRatings = 0;

        for (Rating rating1 : rating) {
            totalPoints += rating1.getPoint();
            totalRatings++;
        }

        if (totalRatings > 0) {
            double averageRating = (double) totalPoints / totalRatings;
            return averageRating;
        } else {
            return 3; // or any other default value when there are no ratings
        }
    }

}
