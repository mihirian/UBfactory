package com.example.ubfactory.controller;

import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.CustomerHelper;
import com.example.ubfactory.objects.ChangePasswordRequest;
import com.example.ubfactory.objects.CustomerObject;
import com.example.ubfactory.objects.GenricResponse;
import com.example.ubfactory.objects.ResetPassword;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerHelper customerHelper;

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


    private final Map<String, String> otpStore = new ConcurrentHashMap<>();

    @PostMapping("forgot-password/{email}")
    public ResponseEntity<Object> forgotPassword(@PathVariable String email) {
        try {
            String otp = customerHelper.generateOTP();
            customerHelper.sendOTPByEmail(email, otp);
            otpStore.put(email, otp);
            return GenricResponse.genricResponse(ResponseConstants.MAIL_SEND_SUCCESSFULLY, HttpStatus.OK, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }


    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Object> verifyOTP(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String otp = request.get("otp");
            String storedOTP = otpStore.get(email);
            if (storedOTP == null || !storedOTP.equals(otp)) {
                return ResponseEntity.badRequest().body(ResponseConstants.INVALID_OTP);
            }
            otpStore.remove(email);
            return GenricResponse.genricResponse(ResponseConstants.OTP_VERIFICATION, HttpStatus.OK, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/reset/password")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPassword request) throws BusinessException {
        try {
            Response response = customerService.resetPassword(request);
            return GenricResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, response);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


}
