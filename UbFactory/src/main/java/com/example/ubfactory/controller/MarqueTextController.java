package com.example.ubfactory.controller;

import com.example.ubfactory.exception.BusinessException;
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
public class MarqueTextController
{
    @Autowired
    private MarqueTextService marqueTextService;
    @PostMapping("/addMarqueText")
    public ResponseEntity<MarqueTextResponse> createProduct(@RequestBody MarqueTextRequest marqueTextRequest) throws BusinessException {
        MarqueTextResponse marqueTextResponse = marqueTextService.addMarqueText(marqueTextRequest);
        return new  ResponseEntity<>(marqueTextResponse, HttpStatus.CREATED);
    }
}
