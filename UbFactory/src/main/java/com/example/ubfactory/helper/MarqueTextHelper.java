package com.example.ubfactory.helper;

import com.example.ubfactory.entities.Marque;
import com.example.ubfactory.objects.MarqueTextRequest;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class MarqueTextHelper {

    public Marque getMarqueeObject(MarqueTextRequest validateRequest) {
        Calendar cal = Calendar.getInstance();
        Marque marqueeText = new Marque();
        cal.setTime(validateRequest.getEndDate());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        marqueeText.setEndDate(cal.getTime());
        marqueeText.setMarqueText(validateRequest.getMarqueText());
        marqueeText.setMarqueeName(validateRequest.getMarqueeName());
        cal.setTime(validateRequest.getStartDate());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        marqueeText.setStartDate(cal.getTime());
        marqueeText.setStatus("ACTIVE");
        marqueeText.setCreatedAt(new Date());
        marqueeText.setUpdatedAt(new Date());
        return marqueeText;
    }

}
