package com.example.ubfactory.helper;

import com.example.ubfactory.entities.OrderSummary;
import com.example.ubfactory.entities.PaymentSummary;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentHelper {
    public static final Logger logger = LoggerFactory.getLogger(PaymentHelper.class);

    @Autowired
    private PaymentRepository paymentRepository;
    public PaymentSummary createPaymentSummary(OrderSummary orderSummary) {
        PaymentSummary paymentSummary =new PaymentSummary();
        paymentSummary.setOrderId(orderSummary.getId());
        paymentSummary.setAmount(orderSummary.getTotalPrice());
        paymentSummary.setCustomerId(orderSummary.getCustomer().getId());
        paymentSummary.setCreatedAt(new Date());
        paymentSummary.setUpdatedAt(new Date());
        paymentSummary.setPaymentStatus(Status.INITIATED.getStatus());
        paymentSummary.setCurrency("INR");
        paymentRepository.save(paymentSummary);
       return paymentSummary;
    }
}
