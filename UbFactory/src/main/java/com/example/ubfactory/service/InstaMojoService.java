package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.*;
import com.razorpay.RazorpayException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface InstaMojoService {
    OrderResponseObject createOrder(OrderRequestObject orderRequestObject) throws BusinessException;
    CapturePaymentResponse capturePayment(InstaMojoCallBackRequest orderRequestObject) throws BusinessException, RazorpayException;


    UpdateOrderObject updateOrderSummery(UpdateOrderObject updateOrderObject) throws BusinessException;


    Page<SearchOrderObjectByDate> searchDateByDate(SearchOrderObjectByDate searchOrderObjectByDate, Pageable pageable) throws ParseException;
}
