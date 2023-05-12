package com.example.ubfactory.validator;

import com.example.ubfactory.entities.Banner;
import com.example.ubfactory.objects.BannerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BannerValidator {
    public static final Logger logger = LoggerFactory.getLogger(BannerValidator.class);

    public void validateBannerRequest(BannerObject bannerObject) {
        if(bannerObject.getName() == null){

        }if(bannerObject.getUrl() == null){

        }if(bannerObject.getEndDate() == null){

        }if(bannerObject.getStartDate() == null){

        }

    }
}
