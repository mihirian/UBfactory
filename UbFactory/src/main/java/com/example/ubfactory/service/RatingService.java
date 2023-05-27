package com.example.ubfactory.service;

import com.example.ubfactory.objects.RatingRequestObject;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RatingService {
    String createRating(RatingRequestObject ratingRequestObject);

    RatingRequestObject getByProductId(Integer productId);
}
