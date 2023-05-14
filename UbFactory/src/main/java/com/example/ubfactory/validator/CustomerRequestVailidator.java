package com.example.ubfactory.validator;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.CustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerRequestVailidator {

    public CustomerRequest validateCutomerRequest(CustomerRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException(1101, "Invalid request");
        }
        if (request.getFirstName().isBlank()) {
            throw new BusinessException(1102, "Invailid firstname");
        }
        if (request.getLastName().isBlank()) {
            throw new BusinessException(1103, "Invailid lastname");
        }
        if (request.getEmail().isBlank()) {
            throw new BusinessException(1104, "Invailid email");
        }
        if (request.getMobile().isBlank()) {
            throw new BusinessException(1105, "Invalid mobile number");
        }
        return request;
    }
}
