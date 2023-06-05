package com.example.ubfactory.validator;

import com.example.ubfactory.entities.Cart;
import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.repository.CartRepository;
import com.example.ubfactory.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;

@Component
public class OrderVaildator {
    public static final Logger logger = LoggerFactory.getLogger(OrderVaildator.class);
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartRepository cartRepository;

    public Cart validateOrderRequest(OrderRequestObject orderRequestObject) throws BusinessException {
        Customer customer = customerRepository.findById(orderRequestObject.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Cart cart = cartRepository.findByCustomer(customer).orElseThrow(() -> new EntityNotFoundException("cart not found"));
//        if(ObjectUtils.isEmpty(orderRequestObject.getProductId())){
//           throw new BusinessException("Product id cannot be null");
//        }
        return cart;
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
