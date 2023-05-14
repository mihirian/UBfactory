package com.example.ubfactory.service;

import com.example.ubfactory.objects.BannerObject;

import java.util.List;

public interface BannerService {
    BannerObject saveBanner(BannerObject bannerObject);

    List<BannerObject> getBanner();
}
