package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.CapturePaymentResponse;
import com.example.ubfactory.objects.InstaMojoCallBackRequest;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.objects.OrderResponseObject;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;

@Service
public interface InstaMojoService {
    OrderResponseObject createOrder(OrderRequestObject orderRequestObject) throws BusinessException;
    CapturePaymentResponse capturePayment(InstaMojoCallBackRequest orderRequestObject) throws BusinessException, RazorpayException;

}
