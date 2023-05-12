package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Marque;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.MarqueTextHelper;
import com.example.ubfactory.objects.MarqueTextRequest;
import com.example.ubfactory.objects.MarqueTextResponse;
import com.example.ubfactory.repository.MarqueRepository;
import com.example.ubfactory.service.MarqueTextService;
import com.example.ubfactory.validator.MarqueTextVailidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarqueTextServiceImp implements MarqueTextService
{
    @Autowired
    private MarqueTextVailidator marqueTextVailidator;
    @Autowired
    private MarqueTextHelper marqueTextHelper;
    @Autowired
    private MarqueRepository marqueRepository;


    @Override
    public MarqueTextResponse addMarqueText(MarqueTextRequest marqueTextRequest) throws BusinessException {
        MarqueTextRequest validateRequest=marqueTextVailidator.validateMarqueTextRequest(marqueTextRequest);
        Marque marqueeText = marqueTextHelper.getMarqueeObject(validateRequest);
        marqueeText = marqueRepository.save(marqueeText);
        MarqueTextResponse marqueTextResponse=new MarqueTextResponse();
        marqueTextResponse.setId(Long.valueOf(marqueeText.getId()));
        marqueTextResponse.setStatus(marqueeText.getStatus());
        return marqueTextResponse;
    }
}

