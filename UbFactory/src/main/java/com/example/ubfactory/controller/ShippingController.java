package com.example.ubfactory.controller;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.BannerObject;
import com.example.ubfactory.objects.GenericResponse;
import com.example.ubfactory.objects.ShippingObject;
import com.example.ubfactory.service.ShippingService;
import com.example.ubfactory.service.serviceimpl.ShippingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ShippingController {
    public static final Logger logger = LoggerFactory.getLogger(ShippingController.class);

    @Autowired
    private ShippingService shippingService;

    @PostMapping()
    public ResponseEntity<Object> createShipping(@RequestBody ShippingObject bannerObject) throws BusinessException {
        try {
            logger.info("Getting request from ui for create banner{}" , bannerObject);
            ShippingObject saveBanner = shippingService.shippingService(bannerObject);
            return GenericResponse.genericResponse("Success", HttpStatus.CREATED, saveBanner);
//        } catch (BusinessException b) {
//            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}