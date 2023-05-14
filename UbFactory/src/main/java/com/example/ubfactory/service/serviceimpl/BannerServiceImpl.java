package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Banner;
import com.example.ubfactory.helper.BannerHelper;
import com.example.ubfactory.objects.BannerObject;
import com.example.ubfactory.repository.BannerRepository;
import com.example.ubfactory.service.BannerService;
import com.example.ubfactory.validator.BannerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class BannerServiceImpl implements BannerService {
    public static final Logger logger = LoggerFactory.getLogger(BannerServiceImpl.class);
    @Autowired
    private BannerValidator bannerValidator;
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private BannerHelper bannerHelper;


    @Override
    public BannerObject saveBanner(BannerObject bannerObject) {
        bannerValidator.validateBannerRequest(bannerObject);
        bannerObject.setCreatedAt(new Date());
        bannerObject.setUpdateAt(new Date());
        Banner bannerEntity = bannerHelper.bannerObjToEntity(bannerObject);
        bannerRepository.save(bannerEntity);
       // return bannerHelper.entityToBannerObj(bannerEntity);
        return null;
    }

    @Override
    public BannerObject getBanner() {
        return null;
    }
}
