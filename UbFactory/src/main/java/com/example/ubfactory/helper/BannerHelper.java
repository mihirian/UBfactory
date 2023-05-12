package com.example.ubfactory.helper;

import com.example.ubfactory.entities.Banner;
import com.example.ubfactory.objects.BannerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BannerHelper {
    public static final Logger logger = LoggerFactory.getLogger(BannerHelper.class);

    public Banner bannerObjToEntity(BannerObject bannerObject1) {
        Banner banner = new Banner();
        banner.setName(bannerObject1.getName());
        return null;
    }
}
