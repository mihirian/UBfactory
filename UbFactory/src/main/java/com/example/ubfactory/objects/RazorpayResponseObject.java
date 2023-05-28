package com.example.ubfactory.objects;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class RazorpayResponseObject {
    public static final Logger logger = LoggerFactory.getLogger(RazorpayResponseObject.class);

    private Integer amount;
    private String currency;
    private String id;
    private String entity;
    private Integer amount_paid;
    private Integer amount_due;
    private String receipt;
    private String offer_id;
    private String status;
    private Integer attempts;
    private String created_at;
}
