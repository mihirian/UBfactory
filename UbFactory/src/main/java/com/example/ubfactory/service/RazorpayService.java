package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.CapturePaymentResponse;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.objects.OrderResponseObject;
import com.example.ubfactory.objects.RazorpayRequestObject;
import com.razorpay.RazorpayException;


public interface RazorpayService {
    OrderResponseObject createOrder(OrderRequestObject orderRequestObject) throws BusinessException;

    CapturePaymentResponse capturePayment(OrderRequestObject orderRequestObject) throws BusinessException, RazorpayException;
}
