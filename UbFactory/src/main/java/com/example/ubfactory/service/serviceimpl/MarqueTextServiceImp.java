package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Marque;
import com.example.ubfactory.enums.Status;
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
public class MarqueTextServiceImp implements MarqueTextService {
    @Autowired
    private MarqueTextVailidator marqueTextVailidator;
    @Autowired
    private MarqueTextHelper marqueTextHelper;
    @Autowired
    private MarqueRepository marqueRepository;


    @Override
    public MarqueTextResponse addMarqueText(MarqueTextRequest marqueTextRequest) throws BusinessException {
        MarqueTextRequest validateRequest = marqueTextVailidator.validateMarqueTextRequest(marqueTextRequest);
        Marque marqueeText = marqueTextHelper.getMarqueeObject(validateRequest);
        marqueeText = marqueRepository.save(marqueeText);
        MarqueTextResponse marqueTextResponse = new MarqueTextResponse();
        marqueTextResponse.setId(Long.valueOf(marqueeText.getId()));
        marqueTextResponse.setStatus(marqueeText.getStatus());
        return marqueTextResponse;
    }

    @Override
    public MarqueTextResponse searchMarqueText(MarqueTextRequest marqueTextRequest) throws BusinessException {
        MarqueTextRequest request = marqueTextVailidator.validateSearchRequest(marqueTextRequest);
        Marque marque = marqueRepository.findByMarqueeName(request.getMarqueeName());
        MarqueTextResponse response = new MarqueTextResponse();
        if (marque.getStatus().equals(Status.ACTIVE.getStatus())) {
            response.setId(Long.valueOf(marque.getId()));
            response.setMarqueeText(marque.getMarqueText());
            response.setStatus(marque.getStatus());
            response.setMarqueeName(marque.getMarqueeName());
        }
        return response;
    }

}

