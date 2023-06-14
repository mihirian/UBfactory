package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.objects.OrderResponseObject;
import org.springframework.stereotype.Service;

@Service
public interface InstaMojoService {
    OrderResponseObject createOrder(OrderRequestObject orderRequestObject) throws BusinessException;
}
