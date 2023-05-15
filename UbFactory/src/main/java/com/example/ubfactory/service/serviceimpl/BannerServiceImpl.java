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
import java.util.List;
import java.util.stream.Collectors;


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
    public List<BannerObject> getBanner() {
        List<Banner> bannerList = bannerRepository.findBannerListBystartDate(new Date());
        List<BannerObject> responseList = bannerList.stream().map(banner -> {
            BannerObject bannerObject = new BannerObject();
            bannerObject.setUrl(banner.getUrl());
            bannerObject.setName(banner.getName());
            bannerObject.setStatus(banner.getStatus());
            return bannerObject;
        }).collect(Collectors.toList());
        return responseList;
    }
}
