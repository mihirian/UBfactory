package com.example.ubfactory.controller;

//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
import com.example.ubfactory.objects.GenricResponse;
import com.example.ubfactory.objects.RazorpayRequestObject;
import com.example.ubfactory.service.RazorpayService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/razorpay")
public class RazorpayController {
    public static final Logger logger = LoggerFactory.getLogger(RazorpayController.class);

    @Autowired
    private RazorpayService razorpayService;

//    @PostMapping()
//    public ResponseEntity<Object>  createOrder(@RequestBody RazorpayRequestObject razorpayRequestObject){
//        try{
//            RazorpayRequestObject requestObject = razorpayService.createOrder(razorpayRequestObject);
//            return GenricResponse.genricResponse("Success", HttpStatus.CREATED, requestObject);
//
//        }catch (Exception e) {
//            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
//        }


        @PostMapping("/create-order")
        public ResponseEntity<?> createOrder(@RequestBody RazorpayRequestObject razorpayRequestObject) {
            try {
                RazorpayRequestObject requestObject = razorpayService.createOrder(razorpayRequestObject);
                return GenricResponse.genricResponse("Success", HttpStatus.CREATED, requestObject);
            } catch (Exception e) {
                return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
            }
        }

        @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
        public ResponseEntity<?> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
            // Log the exception and return an appropriate response to the client
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid MIME type in 'Accept' header");
        }
    }


