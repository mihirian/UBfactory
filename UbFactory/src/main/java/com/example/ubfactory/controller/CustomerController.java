package com.example.ubfactory.controller;

import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.ChangePasswordRequest;
import com.example.ubfactory.objects.CustomerObject;
import com.example.ubfactory.objects.GenricResponse;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer/registration")
    public ResponseEntity<Object> customerRegistration(@RequestBody CustomerObject request) throws BusinessException {
        try {
            Response response = customerService.customerRegistration(request);
            return GenricResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.CREATED, response);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    @GetMapping("/fetch/all_customer")
    public ResponseEntity<Object> fetchAllCustomerDetail() throws BusinessException {
        try {
            List<CustomerObject> response = customerService.fetchAllCustomerDetail();
            return GenricResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, response);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PutMapping("/update/customer_byid/{id}")
    public ResponseEntity<Object> updateCustomerDetailById(@RequestBody CustomerObject customerObject ,@PathVariable int id) throws BusinessException {
        try {
            Response response = customerService.updateCustomerDetailById(customerObject,id);
            return GenricResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, response);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @PutMapping("/change/password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws BusinessException {
        try {
            Response response = customerService.changePassword(changePasswordRequest);
            return GenricResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, response);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }



}
