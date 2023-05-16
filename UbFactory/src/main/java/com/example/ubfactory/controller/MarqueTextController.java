package com.example.ubfactory.controller;

import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.GenericResponse;
import com.example.ubfactory.objects.MarqueTextRequest;
import com.example.ubfactory.objects.MarqueTextResponse;
import com.example.ubfactory.service.MarqueTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarqueTextController {
    @Autowired
    private MarqueTextService marqueTextService;

    @PostMapping("/addMarqueText")
    public ResponseEntity<Object> addMarqueText(@RequestBody MarqueTextRequest marqueTextRequest) throws BusinessException {
        try {
            MarqueTextResponse marqueTextResponse = marqueTextService.addMarqueText(marqueTextRequest);
            return GenericResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.CREATED, marqueTextResponse);
        } catch (BusinessException b) {
            return GenericResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenericResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/searchMarqueText")
    public ResponseEntity<Object> serchMarqueText(@RequestBody MarqueTextRequest marqueTextRequest) throws BusinessException {
        try {
            MarqueTextResponse marqueTextResponse = marqueTextService.searchMarqueText(marqueTextRequest);
            return GenericResponse.genricResponse(Status.SUCCESS.getStatus(), HttpStatus.CREATED, marqueTextResponse);
        } catch (BusinessException b) {
            return GenericResponse.genricResponse(b.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return GenericResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


}
