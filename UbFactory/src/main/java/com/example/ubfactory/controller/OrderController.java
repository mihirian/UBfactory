package com.example.ubfactory.controller;

//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
import com.example.ubfactory.objects.GenricResponse;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.objects.OrderResponseObject;
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
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private RazorpayService razorpayService;

    @PostMapping("/create-order")

    //create order
     public ResponseEntity<?> createOrder(@RequestBody OrderRequestObject orderRequestObject) {
         try {
             OrderResponseObject requestObject = razorpayService.createOrder(orderRequestObject);
             return GenricResponse.genricResponse("Success", HttpStatus.CREATED, requestObject);
         } catch (Exception e) {
             return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
         }
     }
    }


