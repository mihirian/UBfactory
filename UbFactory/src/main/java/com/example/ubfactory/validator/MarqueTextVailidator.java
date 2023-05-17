package com.example.ubfactory.validator;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.MarqueTextRequest;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.stereotype.Component;

@Component
public class MarqueTextVailidator {

    public MarqueTextRequest validateMarqueTextRequest(MarqueTextRequest marqueTextRequest) throws BusinessException {
        if (marqueTextRequest == null) {
            throw new BusinessException(719, ResponseConstants.INVAILID_REQUEST);
        }
        if (marqueTextRequest.getEndDate() == null) {
            throw new BusinessException(40002, ResponseConstants.END_DATE_NOT_FOUND);
        }
        if (marqueTextRequest.getMarqueText() == null) {
            throw new BusinessException(6000001, ResponseConstants.MARQUEE_TEXT_NOT_FOUND);
        }
        if (marqueTextRequest.getStartDate() == null) {
            throw new BusinessException(40001, ResponseConstants.START_DATE_NOT_FOUND);
        }

        return marqueTextRequest;
    }

    public MarqueTextRequest validateSearchRequest(MarqueTextRequest marqueTextRequest) throws BusinessException {
        if (marqueTextRequest == null) {
            throw new BusinessException(719, ResponseConstants.INVAILID_REQUEST);
        }
        return marqueTextRequest;
    }
}

