package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Marque;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.MarqueTextHelper;
import com.example.ubfactory.objects.GenricResponse;
import com.example.ubfactory.objects.MarqueTextRequest;
import com.example.ubfactory.objects.MarqueTextResponse;
import com.example.ubfactory.repository.MarqueRepository;
import com.example.ubfactory.service.MarqueTextService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import com.example.ubfactory.validator.MarqueTextVailidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public MarqueTextResponse searchMarqueText(String marqueeName) throws BusinessException {
        Marque marque = marqueRepository.findByMarqueeName(marqueeName);
        if(marque==null)
        {
            throw new BusinessException(ResponseConstants.MARQUEE_TEXT_NOT_FOUND);
        }
        MarqueTextResponse response = new MarqueTextResponse();
        if (marque.getStatus().equals(Status.ACTIVE.getStatus())) {
            response.setId(Long.valueOf(marque.getId()));
            response.setMarqueeText(marque.getMarqueText());
            response.setStatus(marque.getStatus());
            response.setMarqueeName(marque.getMarqueeName());
        }
        return response;
    }

    @Override
    public Response<Marque> updateMarqueeText(MarqueTextRequest marqueTextRequest) throws BusinessException {
        GenricResponse<Marque> response=new GenricResponse<>();
        Marque marque = marqueRepository.findByMarqueeName(marqueTextRequest.getMarqueeName());
        if(marque==null)
        {
            throw new BusinessException(ResponseConstants.FAILURE);
        }
        marque.setMarqueText(marqueTextRequest.getMarqueText());
        marque.setStartDate(marqueTextRequest.getStartDate());
        marque.setEndDate(marqueTextRequest.getEndDate());
        marqueRepository.save(marque);
        return response.createSuccessResponse(null,HttpStatus.OK.value(), ResponseConstants.UPDATE_SUCCESSFULLY);

    }


}

