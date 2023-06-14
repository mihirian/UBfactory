package com.example.ubfactory.controller;

//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;

import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.CapturePaymentResponse;
import com.example.ubfactory.objects.GenericResponse;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.objects.OrderResponseObject;
import com.example.ubfactory.service.InstaMojoService;
import com.example.ubfactory.service.RazorpayService;
import com.example.ubfactory.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/razorpay")
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private RazorpayService razorpayService;
    @Autowired
    private InstaMojoService instaMojoService;

    @PostMapping("/create-order")

    //create order
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestObject orderRequestObject) {
        try {
            OrderResponseObject requestObject = razorpayService.createOrder(orderRequestObject);
            return GenericResponse.genericResponse("Success", HttpStatus.CREATED, requestObject);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/capture/payment")
    public ResponseEntity<?> capturePayment(@RequestBody OrderRequestObject orderRequestObject) {
        try {
            CapturePaymentResponse requestObject = razorpayService.capturePayment(orderRequestObject);
            return GenericResponse.genericResponse("Success", HttpStatus.CREATED, requestObject);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("billgenrater/{id}")
    public ResponseEntity<Object> billGenrater(@PathVariable int id) throws BusinessException {
        try {
            Response response = razorpayService.billGenrater(id);
            return GenericResponse.genericResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, response);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    @PostMapping("/instamojo/create-order")

    //create order
    public ResponseEntity<?> createPaymentOrder(@RequestBody OrderRequestObject orderRequestObject) {
        try {
            OrderResponseObject requestObject = instaMojoService.createOrder(orderRequestObject);
            return GenericResponse.genericResponse("Success", HttpStatus.CREATED, requestObject);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    @PostMapping("/instamojo/create-order")
    //create order
    public ResponseEntity<?> capturePaymentInstaMojo(@RequestBody OrderRequestObject orderRequestObject) {
        try {
            OrderResponseObject requestObject = instaMojoService.createOrder(orderRequestObject);
            return GenericResponse.genericResponse("Success", HttpStatus.CREATED, requestObject);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


}


