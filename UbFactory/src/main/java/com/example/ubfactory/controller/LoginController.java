package com.example.ubfactory.controller;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.JwtTokenHelper;
import com.example.ubfactory.objects.CustomerObject;
import com.example.ubfactory.objects.GenericResponse;
import com.example.ubfactory.objects.LoginRequest;
import com.example.ubfactory.objects.LoginResponse;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.service.serviceimpl.LoginServiceImp;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private LoginServiceImp userDetailsSer;
    @Autowired
    private JwtTokenHelper jwtHelper;
    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> generateToken(@RequestBody LoginRequest request) throws Exception {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), (request.getPassword())));
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Badcredential");
        } catch (BadCredentialsException b) {
            return GenericResponse.genericResponse("Incorrect email or password", HttpStatus.BAD_REQUEST, null);
        }
        LoginResponse loginResponse = this.userDetailsSer.getUserByName(request.getEmail());
        return GenericResponse.genericResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, loginResponse);
    }


    @GetMapping("logout/{ownerId}")
    public Response<Customer> logout(@PathVariable Integer ownerId) throws BusinessException
    {      GenericResponse<Customer> response = new GenericResponse<>();
        try {
            CustomerObject  customerObject = customerService.logout(ownerId);
            return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.LOGOUT_SUCCESSFULLY);

        } catch (BusinessException b) {
            return response.createErrorResponse(b.getErrorCode(), b.getLocalizedMessage());
        } catch (Exception e) {
            return response.createErrorResponse(401,ResponseConstants.REQUEST_TIME_OUT);
        }
    }
}
