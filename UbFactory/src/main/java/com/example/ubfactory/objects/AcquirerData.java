package com.example.ubfactory.objects;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class AcquirerData {
    public static final Logger logger = LoggerFactory.getLogger(AcquirerData.class);
    private String authenticationReferenceNumber;
    private String rrn;
    private String authCode;
}
