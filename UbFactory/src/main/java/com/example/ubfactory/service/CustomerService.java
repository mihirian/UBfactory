package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.utils.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<CustomerObject> fetchAllCustomerDetail() throws BusinessException;

    Response updateCustomerDetailById(CustomerObject customerObject, int id) throws BusinessException;

    Response changePassword(ChangePasswordRequest changePasswordRequest) throws BusinessException;


    Response logout(Integer ownerId) throws BusinessException;



    Response addAddress(AddressRequest request) throws BusinessException;

    Response getCustomerDetailById(int id) throws BusinessException;

    Response customerRegistrations(CustomerObject customerObject);

    Response verifyOtp(VerificationRequest request) throws BusinessException;

    Response forgatePassword(String email) throws BusinessException;

    Response forgetPasswordVerifyOtp(ResetPassword request) throws BusinessException;
}
