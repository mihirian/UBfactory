package com.example.ubfactory.helper;

import com.example.ubfactory.entities.OrderSummary;
import com.example.ubfactory.entities.PaymentSummary;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Component
public class PaymentHelper {
    public static final Logger logger = LoggerFactory.getLogger(PaymentHelper.class);

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentSummary createPaymentSummary(OrderSummary orderSummary) {
        PaymentSummary paymentSummary = new PaymentSummary();
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

    public void validateCapturePayment(OrderRequestObject orderRequestObject) throws BusinessException {
        if (ObjectUtils.isEmpty(orderRequestObject.getPaymentId())) {
            throw new BusinessException("Payment id not found.");
        }
        if (ObjectUtils.isEmpty(orderRequestObject.getSignature())) {
            throw new BusinessException("Signature not found");
        }
        if (ObjectUtils.isEmpty(orderRequestObject.getRazorpayId())) {
            throw new BusinessException("razorpay order id not found.");
        }

    }
}
