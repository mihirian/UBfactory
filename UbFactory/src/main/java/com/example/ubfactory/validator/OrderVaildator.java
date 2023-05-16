package com.example.ubfactory.validator;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.OrderRequestObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class OrderVaildator {
    public static final Logger logger = LoggerFactory.getLogger(OrderVaildator.class);

    public void validateOrderRequest(OrderRequestObject orderRequestObject) throws BusinessException {
        if(ObjectUtils.isEmpty(orderRequestObject.getCustomerId())){
           throw new BusinessException("Customer id cannot be null");
        } if(ObjectUtils.isEmpty(orderRequestObject.getProductId())){
           throw new BusinessException("Product id cannot be null");
        }
    }
}
