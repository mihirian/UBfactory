package com.example.ubfactory.controller;

import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.service.RatingService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rating")
public class RatingController {
    public static final Logger logger = LoggerFactory.getLogger(RatingController.class);
    @Autowired
    private RatingService ratingService;

    @PostMapping()
    public Response<?> createRating(@RequestBody RatingRequestObject ratingRequestObject) {
        GenericResponse<String> response = new GenericResponse<>();
        try {
            String response1 = ratingService.createRating(ratingRequestObject);
            return response.createSuccessResponse(response1, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        } catch (Exception e) {
            return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
        }
    }

    @GetMapping("/{productId}")
    public Response<?> getByProductId(@PathVariable Integer productId) {
        GenericResponse<RatingRequestObject> response = new GenericResponse<>();
        try {
            RatingRequestObject response1 = ratingService.getByProductId(productId);
            return response.createSuccessResponse(response1, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        } catch (Exception e) {
            return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
        }
    }

}
