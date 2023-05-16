package com.example.ubfactory.validator;

import com.example.ubfactory.entities.Banner;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.BannerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BannerValidator {
    public static final Logger logger = LoggerFactory.getLogger(BannerValidator.class);

    public void validateBannerRequest(BannerObject bannerObject) throws BusinessException {
        if (bannerObject.getName() == null) {
            throw new BusinessException("Banner name is cannot be null.");
        }
        if (bannerObject.getUrl() == null) {
            throw new BusinessException("Banner image url is cannot be null.");
        }
        if (bannerObject.getEndDate() == null) {
            throw new BusinessException("Please provide banner end date.");
        }
        if (bannerObject.getStartDate() == null) {
            throw new BusinessException("Please provide banner start date.");
        }

    }
}
