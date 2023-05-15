package com.example.ubfactory.controller;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.CustomerRequest;
import com.example.ubfactory.objects.GenricResponse;
import com.example.ubfactory.objects.LoginRequest;
import com.example.ubfactory.objects.LoginResponse;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController
{
    @Autowired
    private CustomerService customerService;
    @PostMapping("/customerLogin")
    public ResponseEntity<Object> customerLogin(@RequestBody LoginRequest request) throws BusinessException {
        try {
            LoginResponse response = customerService.customerLogin(request);
            return GenricResponse.genricResponse("Success", HttpStatus.CREATED, response);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
