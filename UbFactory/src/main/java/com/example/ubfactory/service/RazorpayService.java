package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.objects.RazorpayRequestObject;
import org.springframework.stereotype.Service;


public interface RazorpayService {
    RazorpayRequestObject createRazarpayOrder(RazorpayRequestObject razorpayRequestObject);

    OrderRequestObject createOrder(OrderRequestObject orderRequestObject) throws BusinessException;
}
