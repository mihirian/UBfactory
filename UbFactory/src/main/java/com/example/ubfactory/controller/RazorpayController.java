package com.example.ubfactory.controller;

//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RazorpayController {
    public static final Logger logger = LoggerFactory.getLogger(RazorpayController.class);

    String orderUrl ="https://api.razorpay.com/v1/orders";
//    RazorpayClient razorpay = new RazorpayClient("rzp_test_XVEHHxt1YOPY7q", "szAIjTeJwbucWKaiTK3itpBY");
//
//    public RazorpayController() throws RazorpayException {
    }

