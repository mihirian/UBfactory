package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.CustomerObject;
import com.example.ubfactory.utils.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    Response customerRegistration(CustomerObject request) throws BusinessException;


    List<CustomerObject> fetchAllCustomerDetail() throws BusinessException;

    Response updateCustomerDetailById(CustomerObject customerObject, int id) throws BusinessException;
}
