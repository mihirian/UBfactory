package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.entities.Token;
import com.example.ubfactory.enums.RedisKey;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.CustomerHelper;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.objects.CustomerObject;
import com.example.ubfactory.repository.CustomerRepository;
import com.example.ubfactory.repository.TokenDao;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.utils.RedisService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import com.example.ubfactory.validator.CustomerRequestVailidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private RedisService redisService;


    @Override
    public List<CustomerObject> fetchAllCustomerDetail() throws BusinessException {
        List<CustomerObject> CustomerResponse = new ArrayList<>();
        List<Customer> customersList = customerRepository.findAll();
        if (customersList == null) {
            throw new BusinessException(111, ResponseConstants.CUSTOMER_LIST_NOT_FOUND);
        }
        for (Customer customer : customersList) {
            CustomerObject response = new CustomerObject();
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
        Customer customer = customerRepository.findById(id).get();
        if (ObjectUtils.isEmpty(customer)) {
            throw new BusinessException(112, ResponseConstants.CUSTOMER_DETAIL_NOT_FOUND);
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

    @Override
    public Response changePassword(ChangePasswordRequest changePasswordRequest) throws BusinessException {
        GenricResponse<Customer> response = new GenricResponse<>();
        Optional<Customer> optionalCustomer = customerRepository.findById(changePasswordRequest.getId());

        if (!optionalCustomer.isPresent()) {
            throw new BusinessException(ResponseConstants.CUSTOMER_NOT_FOUND);
        }

        Customer customer = optionalCustomer.get();
        String currentPassword = changePasswordRequest.getCurrentPassword();
        String newPassword = changePasswordRequest.getNewPassword();
        String confirmPassword = changePasswordRequest.getConfirmPassword();

        if (!passwordEncoder.matches(currentPassword, customer.getPassword())) {
            throw new BusinessException(ResponseConstants.PASSWORD_MISMATCH);
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException(ResponseConstants.PASSWORD_MISMATCH);
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedNewPassword);
        customerRepository.save(customer);

        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.CUSTOMER_UPDATED);
    }

    @Override
    @Transactional
    public Response logout(Integer ownerId) throws BusinessException {
        GenricResponse<Customer> response = new GenricResponse<>();
        Token token = tokenDao.findByownerId(ownerId);
        if (token == null) {
            throw new BusinessException(ResponseConstants.FAILURE);
        }
        tokenDao.deleteByownerId(ownerId);

        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.LOGOUT_SUCCESSFULLY);
    }



    @Override
    public Response addAddress(AddressRequest request) throws BusinessException {
        GenricResponse<Customer> response = new GenricResponse<>();
        Customer customer1 = customerRepository.findByemail(request.getEmail());
        if (customer1 == null) {
            throw new BusinessException(ResponseConstants.CUSTOMER_DETAIL_NOT_FOUND);
        }
        customer1.setPinCode(request.getPinCode());
        customer1.setState(request.getState());
        customer1.setTown(request.getTown());
        customer1.setStreetAddress(request.getStreetAddress());
        customerRepository.save(customer1);
        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.SHEEPING_ADDRESS_UPDATE);
    }

    @Override
    public Response getCustomerDetailById(int id) throws BusinessException {
        GenricResponse<Customer> response = new GenricResponse<>();
        Customer customer = customerRepository.findById(id).get();
        if (customer == null) {
            throw new BusinessException(ResponseConstants.CUSTOMER_DETAIL_NOT_FOUND);
        }
        return response.createSuccessResponse(customer, HttpStatus.OK.value(), ResponseConstants.SUCCESS);

    }

    @Override
    public Response customerRegistrations(CustomerObject customerObject) {
        GenricResponse<Customer> response = new GenricResponse<>();

        String email = customerObject.getEmail();
        String otp = customerHelper.generateOTP();
        customerHelper.sendOTPByEmailAddress(email, otp);
        customerObject.setOtp(otp);
        redisService.populateCache(RedisKey.REGISTRATIONKEY.name()+email, customerObject, 60*10);
        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.MAIL_SEND_SUCCESSFULLY);


    }

    @Override
    public Response verifyOtp(VerificationRequest request) throws BusinessException {
        GenricResponse<Customer> response = new GenricResponse<>();
        CustomerObject customerObject = null;

        String email = request.getEmail();
        if (redisService.exists(RedisKey.REGISTRATIONKEY.name()+email)) {
            customerObject = redisService.lookupCache(RedisKey.REGISTRATIONKEY.name()+email, CustomerObject.class);
            String storedOTP = customerObject.getOtp();
            if (storedOTP == null || !storedOTP.equals(request.getOtp())) {
                throw new BusinessException(ResponseConstants.INVALID_OTP);
            }
            Customer customer = customerHelper.getCustomerObject(customerObject);
            customer = customerRepository.save(customer);
            redisService.delete(RedisKey.REGISTRATIONKEY.name() + email);
            return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.CUSTOMER_REGISTERED);

        }
        else {
            throw new BusinessException("key not found");
        }


    }

    @Override
    public Response forgatePassword(String email) throws BusinessException
    {
        GenricResponse<Customer> response = new GenricResponse<>();

        Customer customer=customerRepository.findByemail(email);
        if(customer==null)
        {
            throw new BusinessException("Email not found");
        }

        String otp = customerHelper.generateOTP();
        customerHelper.sendOTPByEmail(email, otp);
        redisService.populateCache(email,otp,60*10);
        return response.createSuccessResponse(null,HttpStatus.OK.value(), ResponseConstants.MAIL_SEND_SUCCESSFULLY);
    }

    @Override
    public Response forgetPasswordVerifyOtp(ResetPassword request) throws BusinessException {
        GenricResponse<Customer> response = new GenricResponse<>();
        String email = request.getEmail();
        String storedOTP = redisService.getFromCache(email);
        if (storedOTP == null || !storedOTP.equals(request.getOtp())) {
            throw new BusinessException(ResponseConstants.INVALID_OTP);
        }
        Customer customer = customerRepository.findByemail(email);
        String newPassword=passwordEncoder.encode(request.getNewPassword());
        customer.setPassword(newPassword);
        customerRepository.save(customer);
        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.PASSWORD_RESET);

    }

}


