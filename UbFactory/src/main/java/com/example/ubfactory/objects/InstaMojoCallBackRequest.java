package com.example.ubfactory.objects;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Data
public class InstaMojoCallBackRequest {
    public static final Logger logger = LoggerFactory.getLogger(InstaMojoCallBackRequest.class);
    private String amount;
    private String buyer;
    private String buyer_name;
    private String buyer_phone;
    private String currency;
    private String fees;
    private String longurl;
    private String mac;
    private String payment_id;
    private String payment_request_id;
    private String purpose;
    private String shorturl;
    private String status;



}
