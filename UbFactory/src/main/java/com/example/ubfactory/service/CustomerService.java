package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.CustomerRequest;
import com.example.ubfactory.objects.LoginRequest;
import com.example.ubfactory.objects.LoginResponse;
import com.example.ubfactory.utils.Response;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    Response customerRegistration(CustomerRequest request) throws BusinessException;

}