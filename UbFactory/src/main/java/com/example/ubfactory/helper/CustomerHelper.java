package com.example.ubfactory.helper;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.objects.CustomerObject;
import com.example.ubfactory.repository.CustomerRepository;
import com.example.ubfactory.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class CustomerHelper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerObject(CustomerObject cutomerObject) {
        Customer customer = new Customer();
        customer.setFirstName(cutomerObject.getFirstName());
        customer.setLastName(cutomerObject.getLastName());
        customer.setMobile(cutomerObject.getMobile());
        customer.setEmail(cutomerObject.getEmail());
        String encodedPassword = passwordEncoder.encode(cutomerObject.getPassword());
        customer.setOwnerType(cutomerObject.getOwnertype());
        customer.setPassword(encodedPassword);
        customer.setOwnerId(cutomerObject.getOwnerId());
        customer.setCreatedAt(new Date());
        customer.setUpdatedAt(new Date());
        return customer;
    }


}
