package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.CustomerHelper;
import com.example.ubfactory.objects.CustomerObject;
import com.example.ubfactory.objects.GenricResponse;
import com.example.ubfactory.objects.LoginRequest;
import com.example.ubfactory.objects.LoginResponse;
import com.example.ubfactory.objects.CustomerObject;
import com.example.ubfactory.repository.CustomerRepository;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import com.example.ubfactory.validator.CustomerRequestVailidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomerRequestVailidator cutomerRequestVailidator;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerHelper customerHelper;

    @Override
    public Response<Customer> customerRegistration( CustomerObject request) throws BusinessException {
        GenricResponse<Customer> response = new GenricResponse<>();
        CustomerObject customerObject = cutomerRequestVailidator.validateCutomerRequest(request);
        Customer customer = customerHelper.getCustomerObject(customerObject);
        customer = customerRepository.save(customer);
        return response.createSuccessResponse(customer, HttpStatus.OK.value(), ResponseConstants.CUSTOMER_REGISTERED);
    }

    @Override
    public List<CustomerObject> fetchAllCustomerDetail() throws BusinessException {
        List<CustomerObject> CustomerResponse=new ArrayList<>();
        List<Customer> customersList=customerRepository.findAll();
        if(customersList==null)
        {
            throw new BusinessException(111, ResponseConstants.CUSTOMER_LIST_NOT_FOUND);
        }
        for(Customer customer:customersList)
        {
            CustomerObject response=new CustomerObject();
            response.setId(customer.getId());
            response.setFirstName(customer.getFirstName());
            response.setLastName(customer.getLastName());
            response.setEmail(customer.getEmail());
            response.setMobile(customer.getMobile());
            response.setPassword(customer.getPassword());
            response.setOwnertype(customer.getOwnerType());
            CustomerResponse.add(response);
        }
        return CustomerResponse;
    }
    @Override
    public Response updateCustomerDetailById(CustomerObject customerObject, int id) throws BusinessException {
        GenricResponse<Customer> response = new GenricResponse<>();
         Customer customer=customerRepository.findById(id).get();
         if(ObjectUtils.isEmpty(customer))
         {
             throw  new BusinessException(112,ResponseConstants.CUSTOMER_DETAIL_NOT_FOUND);
         }
        customer.setFirstName(customerObject.getFirstName());
        customer.setLastName(customerObject.getLastName());
        customer.setEmail(customerObject.getEmail());
        customer.setMobile(customerObject.getMobile());
        customer.setPassword(customerObject.getPassword());
        customer.setOwnerType(customerObject.getOwnertype());
        customerRepository.save(customer);
        return response.createSuccessResponse(customer, HttpStatus.OK.value(), ResponseConstants.CUSTOMER_UPDATED);


    }


}
