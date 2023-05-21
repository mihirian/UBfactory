package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.entities.Token;
import com.example.ubfactory.helper.JwtTokenHelper;
import com.example.ubfactory.objects.LoginRequest;
import com.example.ubfactory.objects.LoginResponse;
import com.example.ubfactory.repository.CustomerRepository;
import com.example.ubfactory.repository.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class LoginServiceImp implements UserDetailsService
{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private TokenDao tokenDao;
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer user = customerRepository.findByemail(email);
        System.out.println(email);
        if (user!=null && email.equals(user.getEmail())) {

            return new User(user.getEmail(),user.getPassword(), new ArrayList<>());
        } else {

            throw new UsernameNotFoundException("user not found with this email !!!!" + email);
        }
    }
    public LoginResponse getUserByName(String email)
    {
        Token tok=new Token();
        Customer user = customerRepository.findByemail(email);
        LoginResponse loginResponse = new LoginResponse();
        if (user!=null && email.equals(user.getEmail())) {
            UserDetails userDetails = loadUserByUsername(email);
            String token = this.jwtTokenHelper.generateToken(userDetails);
            tok.setToken(token);
            tok.setOwnerId(user.getId());
            tokenDao.save(tok);
            loginResponse.setOwnerId(Long.valueOf(user.getId()));
            loginResponse.setOwnerType(user.getOwnerType());
            loginResponse.setToken(token);
        }
        return loginResponse;

    }
}
