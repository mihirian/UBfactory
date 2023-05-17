package com.example.ubfactory.validator;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.CustomerRequest;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.stereotype.Component;

@Component
public class CustomerRequestVailidator {

    public CustomerRequest validateCutomerRequest(CustomerRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException(1101, ResponseConstants.INVAILID_REQUEST);
        }
        if (request.getFirstName().isBlank()) {
            throw new BusinessException(1102, ResponseConstants.INVAILID_FIRST_NAME);
        }
        if (request.getLastName().isBlank()) {
            throw new BusinessException(1103, ResponseConstants.INVAILID_LAST_NAME);
        }
        if (request.getEmail().isBlank()) {
            throw new BusinessException(1104, ResponseConstants.INVAILID_MAIL);
        }
        if (request.getMobile().isBlank()) {
            throw new BusinessException(1105, ResponseConstants.INVAILID_MOBILE_NUMBER);
        }
        return request;
    }
}
