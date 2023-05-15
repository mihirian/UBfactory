package com.example.ubfactory.helper;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.objects.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomerHelper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer getCustomerObject(CustomerRequest cutomerObject) {
        Customer customer = new Customer();
        customer.setFirstName(cutomerObject.getFirstName());
        customer.setLastName(cutomerObject.getLastName());
        customer.setMobile(cutomerObject.getMobile());
        customer.setEmail(cutomerObject.getEmail());
        String encodedPassword = passwordEncoder.encode(cutomerObject.getPassword());
        customer.setPassword(encodedPassword);
        customer.setCreatedAt(new Date());
        customer.setUpdatedAt(new Date());
        return customer;
    }
}
