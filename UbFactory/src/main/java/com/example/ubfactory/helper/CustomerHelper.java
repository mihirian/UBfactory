package com.example.ubfactory.helper;
import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.objects.CustomerObject;
import com.example.ubfactory.repository.CustomerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomerHelper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    public Customer getCustomerObject(CustomerObject cutomerObject) {
        Customer customer = new Customer();
        customer.setFirstName(cutomerObject.getFirstName());
        customer.setLastName(cutomerObject.getLastName());
        customer.setMobile(cutomerObject.getMobile());
        customer.setEmail(cutomerObject.getEmail());
        String encodedPassword = passwordEncoder.encode(cutomerObject.getPassword());
        customer.setOwnerType(cutomerObject.getOwnertype());
        customer.setPassword(encodedPassword);
        customer.setCreatedAt(new Date());
        customer.setUpdatedAt(new Date());
        return customer;
    }


    public String generateOTP()
    {
        int otpLength = 6; // Length of the OTP
        boolean useLetters = false; // Use only digits
        boolean useNumbers = true;
        return RandomStringUtils.random(otpLength, useLetters, useNumbers);
    }

    public void sendOTPByEmail(String email, String otp)
    {
        SimpleMailMessage message
                = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Verification Code");
        message.setText("Dear user,\n\nYou have requested to reset your password. Please use the following verification code to proceed:\n\nVerification Code: " + otp + "\n\nIf you did not initiate this password reset, please ignore this email. Ensure the security of your account and do not share this code with anyone.\n\nThank you,\nThe Support Team");
       javaMailSender.send(message);
    }

    public void sendOTPByEmailAddress(String email, String otp)
    {

        String subject = "Registration Verification Code";
        String text = "Dear user,\n\nThank you for registering with our service. Please use the following verification code to complete your registration:\n\nVerification Code: " + otp + "\n\nIf you did not initiate this registration, please ignore this email.\n\nThank you,\nThe Support Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }
    public Double getLatLngFromZipCode(double lat1,double lon1) throws Exception {
        double lat2 = 12.9389019;
        double lon2 = 77.7292245;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = 6371 * c;
        System.out.println("Distance in KM "+distance);

        return distance;
    }
}
