package com.example.ubfactory.validator;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.MarqueTextRequest;
import org.springframework.stereotype.Component;

@Component
public class MarqueTextVailidator
{

    public MarqueTextRequest validateMarqueTextRequest(MarqueTextRequest marqueTextRequest) throws BusinessException {
        if (marqueTextRequest == null) {
            throw new BusinessException(719, "Invailid Request");
        }
        if (marqueTextRequest.getEndDate() == null) {
            throw new BusinessException(40002, "Invailid start date");
        }
        if (marqueTextRequest.getMarqueText() == null) {
            throw new BusinessException(6000001,"Please enter MarqueeTex");
        }
        if (marqueTextRequest.getStartDate() == null) {
            throw new BusinessException(40001,"Invailid endDate");
        }

        return marqueTextRequest;
    }
}
