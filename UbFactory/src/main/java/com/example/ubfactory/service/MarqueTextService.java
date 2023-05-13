package com.example.ubfactory.service;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.MarqueTextRequest;
import com.example.ubfactory.objects.MarqueTextResponse;
import org.springframework.stereotype.Service;

@Service
public interface MarqueTextService 
{

    MarqueTextResponse addMarqueText(MarqueTextRequest marqueTextRequest) throws BusinessException;

    MarqueTextResponse searchMarqueText(MarqueTextRequest marqueTextRequest) throws BusinessException;
}
