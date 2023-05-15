package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.CustomerHelper;
import com.example.ubfactory.objects.CustomerRequest;
import com.example.ubfactory.objects.LoginRequest;
import com.example.ubfactory.objects.LoginResponse;
import com.example.ubfactory.repository.CustomerRepository;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.validator.CustomerRequestVailidator;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response cutomerRegistration(CustomerRequest request) throws BusinessException {
        Response response = new Response();
        CustomerRequest cutomerObject = cutomerRequestVailidator.validateCutomerRequest(request);
        Customer customer = customerHelper.getCustomerObject(cutomerObject);
        customer = customerRepository.save(customer);
        response.setMessage("You have successfully registered");// we will make enum class
        return response;
    }

    @Override
    public LoginResponse customerLogin(LoginRequest request) throws BusinessException {
        Customer customer = customerRepository.findByemail(request.getEmail());
        if (customer == null) {
            throw new BusinessException(10, "Invalid email");
        }
        if (!BCrypt.checkpw(request.getPassword(), customer.getPassword())) {
            throw new BusinessException(11, "Invalid password");
        }
        return null;
    }
}
