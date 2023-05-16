package com.example.ubfactory.controller;

import com.example.ubfactory.helper.JwtTokenHelper;
import com.example.ubfactory.objects.LoginRequest;
import com.example.ubfactory.objects.LoginResponse;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.service.serviceimpl.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController
{
    @Autowired
    private CustomerService customerService;
    @Autowired
    private LoginServiceImp userDetailsSer;
    @Autowired
    private JwtTokenHelper  jwtHelper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> generateToken(@RequestBody LoginRequest request) throws Exception {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),(request.getPassword())));
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("BadCrdentials");
        } catch (BadCredentialsException b) {
            b.printStackTrace();
            throw new Exception("BadCrdentials");
        }
        LoginResponse loginResponse = this.userDetailsSer.getUserByName(request.getEmail());
        return new ResponseEntity<LoginResponse>(loginResponse,HttpStatus.OK);


    }
}
