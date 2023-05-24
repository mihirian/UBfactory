package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.objects.ShippingObject;
import com.example.ubfactory.repository.ShippingRepository;
import com.example.ubfactory.service.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingServiceImpl implements ShippingService {
    public static final Logger logger = LoggerFactory.getLogger(ShippingServiceImpl.class);
    @Autowired
    private ShippingRepository shippingRepository;

    @Override
    public ShippingObject shippingService(ShippingObject bannerObject) {

        return null;
    }
}
