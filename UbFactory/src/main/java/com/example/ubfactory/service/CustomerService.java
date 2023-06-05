package com.example.ubfactory.service;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.entities.ForgotPassword;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.utils.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<CustomerObject> fetchAllCustomerDetail() throws BusinessException;

    Response updateCustomerDetailById(CustomerObject customerObject, int id) throws BusinessException;

    ChangePasswordObject changePassword(ChangePasswordObject changePasswordRequest) throws BusinessException;


    CustomerObject logout(Integer ownerId) throws BusinessException;


    AddressObject addAddress(AddressObject request) throws BusinessException;

    Customer getCustomerDetailById(int id) throws BusinessException;

    CustomerObject customerRegistrations(CustomerObject customerObject);

    CustomerObject verifyOtp(VerificationRequest request) throws BusinessException;

    ForgotPassword forgatePassword(String email) throws BusinessException;

    ResetPassword forgetPasswordVerifyOtp(ResetPassword request) throws BusinessException;
}
