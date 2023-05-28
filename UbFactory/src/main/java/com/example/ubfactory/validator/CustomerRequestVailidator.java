package com.example.ubfactory.validator;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.AddressRequest;
import com.example.ubfactory.objects.CustomerObject;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.stereotype.Component;

@Component
public class CustomerRequestVailidator {

    public CustomerObject validateCutomerRequest(CustomerObject request) throws BusinessException {
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

    public AddressRequest validateAddressRequest(AddressRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException(ResponseConstants.INVAILID_REQUEST);
        }
        if (request.getPinCode().isBlank()) {
            throw new BusinessException("Please enter the pin code");
        }
        if (request.getLat().isBlank() & request.getLon().isBlank()) {
            throw new BusinessException("lat long not found");
        }


        return request;
    }
}
