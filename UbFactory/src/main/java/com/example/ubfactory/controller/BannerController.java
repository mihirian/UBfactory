package com.example.ubfactory.controller;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.BannerObject;
import com.example.ubfactory.objects.GenericResponse;
import com.example.ubfactory.service.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {
    public static final Logger logger = LoggerFactory.getLogger(BannerController.class);

    @Autowired
    public BannerService bannerService;

    @PostMapping()
    public ResponseEntity<Object> createBanner(@RequestBody BannerObject bannerObject) throws BusinessException {
        try {
            logger.info("Getting request from ui for create banner{}", bannerObject);
            BannerObject saveBanner = bannerService.saveBanner(bannerObject);
            return GenericResponse.genericResponse("Success", HttpStatus.CREATED, saveBanner);
        } catch (BusinessException b) {
            return GenericResponse.genericResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping()
    public ResponseEntity<Object> GetBannerList() {
        try {
            List<BannerObject> saveBanner = bannerService.getBannerList();
            return new ResponseEntity<>(saveBanner, HttpStatus.OK);
        } catch (BusinessException b) {
            return GenericResponse.genericResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
