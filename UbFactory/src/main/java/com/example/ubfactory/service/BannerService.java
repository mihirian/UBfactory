package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.BannerObject;

import java.util.List;

public interface BannerService {
    BannerObject saveBanner(BannerObject bannerObject) throws BusinessException;

    List<BannerObject> getBannerList() throws BusinessException;
}
