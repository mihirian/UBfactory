package com.example.ubfactory.controller;

import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.BannerObject;
import com.example.ubfactory.objects.GenericResponse;
import com.example.ubfactory.service.BannerService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
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
    public Response<BannerObject> createBanner(@RequestBody BannerObject bannerObject) throws BusinessException {
        GenericResponse<BannerObject> response = new GenericResponse<>();
        try {
            logger.info("Getting request from ui for create banner{}", bannerObject);
            BannerObject saveBanner = bannerService.saveBanner(bannerObject);
          return response.createSuccessResponse(saveBanner, HttpStatus.CREATED.value(), ResponseConstants.BANNER_CREATED_SUCCESSFULLY);
        } catch (BusinessException b) {
           return response.createErrorResponse(b.getErrorCode(),b.getMessage());
        } catch (Exception e) {
            return response.createErrorResponse(401,ResponseConstants.REQUEST_TIME_OUT);
        }
    }

    @GetMapping()
    public Response<BannerObject> GetBannerList() {
        GenericResponse<BannerObject> response = new GenericResponse<>();
        try {
            List<BannerObject> saveBanner = bannerService.getBannerList();
            return response.createSuccessListResponse(saveBanner);
        } catch (BusinessException b) {
            return response.createErrorResponse(b.getErrorCode(),b.getMessage());
        } catch (Exception e) {
            return response.createErrorResponse(401,ResponseConstants.REQUEST_TIME_OUT);
        }
    }
}
