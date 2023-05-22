package com.example.ubfactory.objects;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Data
public class CapturePaymentResponse {
    public static final Logger logger = LoggerFactory.getLogger(CapturePaymentResponse.class);
    private String id;
    private String entity;
    private int amount;
    private String currency;
    private String status;
    private String order_id;
    private String invoice_id;
    private boolean international;
    private String method;
    private int amount_refunded;
    private String refund_status;
    private boolean captured;
    private String description;
    private String card_id;
    private String bank;
    private String wallet;
    private String vpa;
    private String email;
    private String contact;
    private String customer_id;
    private String token_id;
    private List<Object> notes;
    private int fee;
    private int tax;
    private Object error_code;
    private Object error_description;
    private Object error_source;
    private Object error_step;
    private Object error_reason;
    private AcquirerData acquirer_data;
    private long created_at;
    private Error error;
}
