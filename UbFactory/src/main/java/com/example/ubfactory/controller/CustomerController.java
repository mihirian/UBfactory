package com.example.ubfactory.controller;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.CustomerRequest;
import com.example.ubfactory.objects.GenricResponse;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/customerRegistration")
    public ResponseEntity<Object> cutomerRegistration(@RequestBody CustomerRequest request) throws BusinessException {
        try {
            Response response = customerService.cutomerRegistration(request);
            return GenricResponse.genricResponse("Success", HttpStatus.CREATED, response);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
