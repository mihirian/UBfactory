package com.example.ubfactory.service;

import com.example.ubfactory.objects.BannerObject;

public interface BannerService {
    BannerObject saveBanner(BannerObject bannerObject);

    BannerObject getBanner();
}
