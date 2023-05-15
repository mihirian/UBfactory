package com.example.ubfactory.service;

import com.example.ubfactory.objects.RazorpayRequestObject;
import org.springframework.stereotype.Service;


public interface RazorpayService {
    RazorpayRequestObject createOrder(RazorpayRequestObject razorpayRequestObject);
}
