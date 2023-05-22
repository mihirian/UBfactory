package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.utils.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    Response customerRegistration(CustomerObject request) throws BusinessException;


    List<CustomerObject> fetchAllCustomerDetail() throws BusinessException;

    Response updateCustomerDetailById(CustomerObject customerObject, int id) throws BusinessException;

    Response changePassword(ChangePasswordRequest changePasswordRequest) throws BusinessException;


    Response logout(Integer ownerId) throws BusinessException;

    Response resetPassword(ResetPassword request) throws BusinessException;

    Response addAddress(AddressRequest request) throws BusinessException;
}
