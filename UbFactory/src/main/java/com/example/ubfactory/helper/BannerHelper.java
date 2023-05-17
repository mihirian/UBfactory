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
        banner.setUrl(bannerObject1.getUrl());
        banner.setStatus(bannerObject1.getStatus());
        banner.setCreatedAt(bannerObject1.getCreatedAt());
        banner.setUpdatedAt(bannerObject1.getUpdateAt());
        banner.setEndDate(bannerObject1.getEndDate());
        banner.setStartDate(bannerObject1.getStartDate());
        return banner;
    }

    public BannerObject entityToBannerObj(Banner bannerEntity) {
        BannerObject bannerObject = new BannerObject();
        bannerObject.setCreatedAt(bannerEntity.getCreatedAt());
        bannerObject.setUpdateAt(bannerEntity.getUpdatedAt());
        bannerObject.setStatus(bannerEntity.getStatus());
        bannerObject.setName(bannerEntity.getName());
        bannerObject.setUrl(bannerEntity.getUrl());
        bannerObject.setEndDate(bannerEntity.getEndDate());
        bannerObject.setStartDate(bannerEntity.getStartDate());
        return bannerObject;
    }
}
