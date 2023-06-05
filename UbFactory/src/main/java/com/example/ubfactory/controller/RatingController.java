package com.example.ubfactory.controller;

import com.example.ubfactory.enums.Status;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.service.RatingService;
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
    public ResponseEntity<?> createRating(@RequestBody RatingRequestObject ratingRequestObject) {
        try {
            String response = ratingService.createRating(ratingRequestObject);
            return GenericResponse.genericResponse(Status.SUCCESS.getStatus(), HttpStatus.CREATED, response);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getByProductId(@PathVariable Integer productId) {
        try {
            RatingRequestObject response = ratingService.getByProductId(productId);
            return GenericResponse.genericResponse(Status.SUCCESS.getStatus(), HttpStatus.CREATED, response);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
