package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.objects.OrderResponseObject;
import com.example.ubfactory.objects.RazorpayRequestObject;


public interface RazorpayService {
    OrderResponseObject createOrder(OrderRequestObject orderRequestObject) throws BusinessException;
}
