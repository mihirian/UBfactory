package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.entities.CustomerCopy;
import com.example.ubfactory.entities.ForgotPassword;
import com.example.ubfactory.entities.Token;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.CustomerHelper;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.repository.CustomerCopyRepo;
import com.example.ubfactory.repository.CustomerRepository;
import com.example.ubfactory.repository.ForgotPasswordRepo;
import com.example.ubfactory.repository.TokenDao;
import com.example.ubfactory.service.CustomerService;
import com.example.ubfactory.utils.RedisService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import com.example.ubfactory.validator.CustomerRequestVailidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    @Autowired
    private CustomerCopyRepo customerCopyRepo;
    @Autowired
    private ForgotPasswordRepo forgotPasswordRepo;


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
        GenericResponse<Customer> response = new GenericResponse<>();
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
        GenericResponse<Customer> response = new GenericResponse<>();
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
        GenericResponse<Customer> response = new GenericResponse<>();
        Token token = tokenDao.findByownerId(ownerId);
        if (token == null) {
            throw new BusinessException(ResponseConstants.FAILURE);
        }
        tokenDao.deleteByownerId(ownerId);

        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.LOGOUT_SUCCESSFULLY);
    }


    @Override
    public Response addAddress(AddressRequest request) throws Exception {
        GenericResponse<Customer> response = new GenericResponse<>();
        AddressRequest request1 = cutomerRequestVailidator.validateAddressRequest(request);

        Customer customer1 = customerRepository.findByemail(request.getEmail());
        if (customer1 == null) {
            throw new BusinessException(ResponseConstants.CUSTOMER_DETAIL_NOT_FOUND);
        }
        Double distence = customerHelper.getLatLngFromZipCode(Double.parseDouble(request1.getLat()), Double.parseDouble(request1.getLon()));
        customer1.setPinCode(request1.getPinCode());
        customer1.setState(request1.getState());
        customer1.setTown(request1.getTown());
        customer1.setStreetAddress(request1.getStreetAddress());
        if (distence <= 6) {
            customer1.setIsDeliverable(true);
            customerRepository.save(customer1);
        } else {
            customer1.setIsDeliverable(false);
            customerRepository.save(customer1);
        }
        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.SHEEPING_ADDRESS_UPDATE);
    }

    @Override
    public Response getCustomerDetailById(int id) throws BusinessException {
        GenericResponse<Customer> response = new GenericResponse<>();
        Customer customer = customerRepository.findById(id).get();
        if (customer == null) {
            throw new BusinessException(ResponseConstants.CUSTOMER_DETAIL_NOT_FOUND);
        }
        return response.createSuccessResponse(customer, HttpStatus.OK.value(), ResponseConstants.SUCCESS);

    }

    @Override
    public Response customerRegistrations(CustomerObject customerObject) {
        GenericResponse<CustomerCopy> response = new GenericResponse<>();

        String email = customerObject.getEmail();
        String otp = customerHelper.generateOTP();
        customerHelper.sendOTPByEmailAddress(email, otp);
        customerObject.setOtp(otp);
        CustomerCopy customerCopy = customerCopyRepo.findByemail(customerObject.getEmail());
        if (customerCopy == null) {
            CustomerCopy newCustomer = new CustomerCopy();
            newCustomer.setFirstName(customerObject.getFirstName());
            newCustomer.setLastName(customerObject.getLastName());
            newCustomer.setMobile(customerObject.getMobile());
            newCustomer.setEmail(customerObject.getEmail());
            String encodedPassword = passwordEncoder.encode(customerObject.getPassword());
            newCustomer.setPassword(encodedPassword);
            newCustomer.setOtp(customerObject.getOtp());
            newCustomer.setOwnerType(customerObject.getOwnertype());
            customerCopyRepo.save(newCustomer);
        } else {
            customerCopy.setFirstName(customerObject.getFirstName());
            customerCopy.setLastName(customerObject.getLastName());
            customerCopy.setMobile(customerObject.getMobile());
            customerCopy.setEmail(customerObject.getEmail());
            String encodedPassword = passwordEncoder.encode(customerObject.getPassword());
            customerCopy.setPassword(encodedPassword);
            customerCopy.setOtp(customerObject.getOtp());
            customerCopy.setOwnerType(customerObject.getOwnertype());
            customerCopyRepo.save(customerCopy);
        }


        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.MAIL_SEND_SUCCESSFULLY);


    }

    @Override
    public Response verifyOtp(VerificationRequest request) throws BusinessException {
        GenericResponse<Customer> response = new GenericResponse<>();
        CustomerObject customerObject = null;

        CustomerCopy customerCopy = customerCopyRepo.findByemail(request.getEmail());
        Customer customer1 = customerRepository.findByemail(request.getEmail());
        if (customer1 != null) {
            throw new BusinessException("Please log in with your existing account or use a different email address for new registrations");
        }
        String givenTime = String.valueOf(customerCopy.getUpdatedAt()) + 0 + 0 + 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

        LocalDateTime givenDateTime = LocalDateTime.parse(givenTime, formatter);
        LocalDateTime newDateTime = givenDateTime.plusMinutes(10);
        String newTime = newDateTime.format(formatter);
        LocalDateTime currentTime = LocalDateTime.now();
        String cTime = currentTime.format(formatter);
        int comparison = cTime.compareTo(newTime);
        if (comparison > 0) {
            throw new BusinessException("The provided OTP has expired. Please generate a new OTP to proceed");
        }
        String storedOTP = customerCopy.getOtp();

        if (storedOTP == null || !storedOTP.equals(request.getOtp())) {
            throw new BusinessException(ResponseConstants.INVALID_OTP);
        }

        Customer customer = customerHelper.getCustomerObject(customerCopy);
        customer = customerRepository.save(customer);

        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.CUSTOMER_REGISTERED);


    }

    @Override
    public Response forgatePassword(String email) throws BusinessException {
        GenericResponse<ForgotPassword> response = new GenericResponse<>();
        Customer customer = customerRepository.findByemail(email);
        if (customer == null) {
            throw new BusinessException(ResponseConstants.CUSTOMER_NOT_FOUND);
        }
        String otp = customerHelper.generateOTP();
        customerHelper.sendOTPByEmail(email, otp);
        ForgotPassword forgotPassword = forgotPasswordRepo.findByemail(email);
        if (forgotPassword == null) {
            ForgotPassword newforgotPassword = new ForgotPassword();
            newforgotPassword.setEmail(email);
            newforgotPassword.setOtp(otp);
            forgotPasswordRepo.save(newforgotPassword);
        } else {
            forgotPassword.setOtp(otp);
            forgotPassword.setEmail(email);
            forgotPasswordRepo.save(forgotPassword);

        }
        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.MAIL_SEND_SUCCESSFULLY);

    }

    @Override
    public Response forgetPasswordVerifyOtp(ResetPassword request) throws BusinessException {
        GenericResponse<Customer> response = new GenericResponse<>();
        ForgotPassword forgotPassword = forgotPasswordRepo.findByemail(request.getEmail());
        String givenTime = String.valueOf(forgotPassword.getUpdatedAt()) + 0 + 0 + 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime givenDateTime = LocalDateTime.parse(givenTime, formatter);
        LocalDateTime newDateTime = givenDateTime.plusMinutes(10);
        String newTime = newDateTime.format(formatter);
        LocalDateTime currentTime = LocalDateTime.now();
        String cTime = currentTime.format(formatter);
        int comparison = cTime.compareTo(newTime);
        if (comparison > 0) {
            throw new BusinessException("The provided OTP has expired. Please generate a new OTP to proceed");
        }
        String storedOTP = forgotPassword.getOtp();
        if (storedOTP == null || !storedOTP.equals(request.getOtp())) {
            throw new BusinessException(ResponseConstants.INVALID_OTP);
        }
        Customer customer = customerRepository.findByemail(request.getEmail());
        String newPassword = passwordEncoder.encode(request.getNewPassword());
        customer.setPassword(newPassword);
        customerRepository.save(customer);
        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.PASSWORD_RESET);

    }

}
