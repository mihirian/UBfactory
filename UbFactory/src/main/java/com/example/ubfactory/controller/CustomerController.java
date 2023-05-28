package com.example.ubfactory.controller;

import com.example.ubfactory.enums.RedisKey;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.CustomerHelper;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.utils.RedisService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerHelper customerHelper;
    @Autowired
    private RedisService redisService;


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

    @GetMapping("get/customer_byid/{id}")
    public ResponseEntity<Object> getCustomerDetailById(@PathVariable int id) throws BusinessException {
        try {
            Response response = customerService.getCustomerDetailById(id);
            return GenricResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, response);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PutMapping("/update/customer_byid/{id}")
    public ResponseEntity<Object> updateCustomerDetailById(@RequestBody CustomerObject customerObject, @PathVariable int id) throws BusinessException {
        try {
            Response response = customerService.updateCustomerDetailById(customerObject, id);
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


    @PostMapping("forgot-password/{email}")
    public ResponseEntity<Object> forgotPassword(@PathVariable String email) {
        try {
            Response response = customerService.forgatePassword(email);
            return GenricResponse.genricResponse(ResponseConstants.MAIL_SEND_SUCCESSFULLY, HttpStatus.OK, null);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    @PostMapping("forgot/password/verify-otp")
    public ResponseEntity<Object> forgetPasswordVerifyOtp(@RequestBody ResetPassword request) {
        try {
            Response response = customerService.forgetPasswordVerifyOtp(request);
            return GenricResponse.genricResponse(ResponseConstants.PASSWORD_RESET, HttpStatus.OK, null);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registrationVerification(@RequestBody CustomerObject customerObject) {
        try {
            Response response = customerService.customerRegistrations(customerObject);
            return GenricResponse.genricResponse(ResponseConstants.SUCCESS, HttpStatus.OK, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @PostMapping("add/address")
    public ResponseEntity<Object> addAddress(@RequestBody AddressRequest request) throws BusinessException {
        try {
            Response response = customerService.addAddress(request);
            return GenricResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, response);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @PostMapping("registration/verify-otp")
    public ResponseEntity<Object> verifyOTP(@RequestBody VerificationRequest request) {
        try {
            Response response = customerService.verifyOtp(request);
            return GenricResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, null);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


}
