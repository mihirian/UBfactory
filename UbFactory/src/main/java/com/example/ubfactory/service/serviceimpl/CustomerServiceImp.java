package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.CustomerHelper;
import com.example.ubfactory.objects.CustomerRequest;
import com.example.ubfactory.objects.GenricResponse;
import com.example.ubfactory.objects.LoginRequest;
import com.example.ubfactory.objects.LoginResponse;
import com.example.ubfactory.repository.CustomerRepository;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import com.example.ubfactory.validator.CustomerRequestVailidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomerRequestVailidator cutomerRequestVailidator;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerHelper customerHelper;

    @Override
    public Response<Customer> customerRegistration(CustomerRequest request) throws BusinessException {
        GenricResponse<Customer> response = new GenricResponse<>();
        CustomerRequest customerObject = cutomerRequestVailidator.validateCutomerRequest(request);
        Customer customer = customerHelper.getCustomerObject(customerObject);
        customer = customerRepository.save(customer);
        return response.createSuccessResponse(customer, HttpStatus.OK.value(), ResponseConstants.CUSTOMER_REGISTERED);
    }

}
