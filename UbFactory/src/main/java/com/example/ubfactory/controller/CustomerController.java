package com.example.ubfactory.controller;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.entities.ForgotPassword;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return GenericResponse.genericResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, response);
        } catch (BusinessException b) {
            return GenericResponse.genericResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("get/customer_byid/{id}")
    public Response<Customer> getCustomerDetailById(@PathVariable int id) throws BusinessException
        {
            GenericResponse<Customer> response = new GenericResponse<>();
            try {
                Customer customer = customerService.getCustomerDetailById(id);
                return response.createSuccessResponse(customer, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
            } catch (BusinessException b) {
                return response.createErrorResponse(b.getErrorCode(), b.getLocalizedMessage());
            } catch (Exception e) {
                return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
            }
        }

        @PutMapping("/update/customer_byid/{id}")
        public ResponseEntity<Object> updateCustomerDetailById (@RequestBody CustomerObject customerObject,
        @PathVariable int id) throws BusinessException {
            try {
                Response response = customerService.updateCustomerDetailById(customerObject, id);
                return GenericResponse.genericResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, response);
            } catch (BusinessException b) {
                return GenericResponse.genericResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
            } catch (Exception e) {
                return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
            }
        }


        @PutMapping("/change/password")
        public Response<ChangePasswordObject> changePassword (@RequestBody ChangePasswordObject changePasswordRequest) throws BusinessException
        {
            GenericResponse<ChangePasswordObject> response = new GenericResponse<>();
            try {
                ChangePasswordObject changePasswordObject = customerService.changePassword(changePasswordRequest);
                return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.PASSWORD_RESET);

            } catch (BusinessException b) {
                return response.createErrorResponse(b.getErrorCode(), b.getLocalizedMessage());
            } catch (Exception e) {
                return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
            }
        }


        @PostMapping("forgot-password/{email}")
        public Response<ForgotPassword> forgotPassword (@PathVariable String email)
        {
            GenericResponse<ForgotPassword> response = new GenericResponse<>();
            try {
                ForgotPassword forgotPassword = customerService.forgatePassword(email);
                return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.MAIL_SEND_SUCCESSFULLY);
            } catch (BusinessException b) {
                return response.createErrorResponse(b.getErrorCode(), b.getLocalizedMessage());
            } catch (Exception e) {
                return response.createErrorResponse(401,ResponseConstants.REQUEST_TIME_OUT);
            }

        }

        @PostMapping("forgot/password/verify-otp")
        public Response<ResetPassword> forgetPasswordVerifyOtp (@RequestBody ResetPassword request)
        {
            GenericResponse<ResetPassword> response = new GenericResponse<>();
            try {
                ResetPassword resetPassword = customerService.forgetPasswordVerifyOtp(request);
                return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.PASSWORD_RESET);
            } catch (BusinessException b) {
                return response.createErrorResponse(b.getErrorCode(), b.getLocalizedMessage());
            } catch (Exception e) {
                return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);

            }
        }

        @PostMapping("/registration")
        public Response<CustomerObject> registrationVerification (@RequestBody CustomerObject customerObject)
        {
            GenericResponse<CustomerObject> response = new GenericResponse<>();
            try {
                CustomerObject customerRegistrations = customerService.customerRegistrations(customerObject);
                return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.REGISTRATION_VERIFICATION_MAIL);
            } catch (Exception e) {
                return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
            }
        }


        @PostMapping("add/address")
        public Response<AddressObject> addAddress (@RequestBody AddressObject request) throws BusinessException
        {
            GenericResponse<AddressObject> response = new GenericResponse<>();
            try {
                AddressObject addressObject = customerService.addAddress(request);
                return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.SHEEPING_ADDRESS_UPDATE);
            } catch (BusinessException b) {
                return response.createErrorResponse(b.getErrorCode(), b.getLocalizedMessage());
            } catch (Exception e) {
                return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
            }
        }

        @PostMapping("registration/verify-otp")
        public Response<CustomerObject> verifyOTP (@RequestBody VerificationRequest request)
        {
            GenericResponse<CustomerObject> response = new GenericResponse<>();
            try {
                CustomerObject customerObject = customerService.verifyOtp(request);
                return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.CUSTOMER_REGISTERED);

            } catch (BusinessException b) {
                return response.createErrorResponse(b.getErrorCode(), b.getLocalizedMessage());
            } catch (Exception e) {
                return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
            }
        }

    }

