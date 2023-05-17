package com.example.ubfactory.controller;

import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.GenricResponse;
import com.example.ubfactory.objects.MarqueTextRequest;
import com.example.ubfactory.objects.MarqueTextResponse;
import com.example.ubfactory.service.MarqueTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MarqueTextController {
    @Autowired
    private MarqueTextService marqueTextService;

    @PostMapping("/addMarqueText")
    public ResponseEntity<Object> addMarqueText(@RequestBody MarqueTextRequest marqueTextRequest) throws BusinessException {
        try {
            MarqueTextResponse marqueTextResponse = marqueTextService.addMarqueText(marqueTextRequest);
            return GenricResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.CREATED, marqueTextResponse);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/searchMarqueText/{marqueeName}")
    public ResponseEntity<Object> serchMarqueText(@PathVariable String marqueeName) throws BusinessException {
        try {
            MarqueTextResponse marqueTextResponse = marqueTextService.searchMarqueText(marqueeName);
            return GenricResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, marqueTextResponse);
        } catch (BusinessException b) {
            return GenricResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


}
