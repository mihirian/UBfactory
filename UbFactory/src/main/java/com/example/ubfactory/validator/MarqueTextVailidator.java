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
            throw new BusinessException(40002, "Invailid end date");
        }
        if (marqueTextRequest.getMarqueText() == null) {
            throw new BusinessException(6000001,"Please enter MarqueeTex");
        }
        if (marqueTextRequest.getStartDate() == null) {
            throw new BusinessException(40001,"Invailid start date");
        }

        return marqueTextRequest;
    }

    public MarqueTextRequest validateSearchRequest(MarqueTextRequest marqueTextRequest) throws BusinessException {
        if (marqueTextRequest == null) {
            throw new BusinessException(719, "Invailid request");
        }
//        if (marqueTextRequest.getStatus() == null) {
//            throw new BusinessException(457,"");
//        }
        return marqueTextRequest;
    }
    }

