package com.example.ubfactory.controller;

import com.example.ubfactory.entities.Product;
import com.example.ubfactory.objects.BannerObject;
import com.example.ubfactory.service.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/banner")
public class BannerController {
    public static final Logger logger = LoggerFactory.getLogger(BannerController.class);

  @Autowired
  public BannerService bannerService;

    @PostMapping("save")
    public ResponseEntity<BannerObject> createBanner(@RequestBody BannerObject bannerObject){
        BannerObject saveBanner = bannerService.saveBanner(bannerObject);
        return new  ResponseEntity<>(saveBanner, HttpStatus.CREATED);
    }

    @GetMapping("get/banner")
    public ResponseEntity<BannerObject> GetBanner(){
        BannerObject saveBanner = bannerService.getBanner();
        return new  ResponseEntity<>(saveBanner, HttpStatus.OK);
    }
}