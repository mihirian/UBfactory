package com.example.ubfactory.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "PaymentSummary")
public class PaymentSummary extends BaseEntity {
    public static final Logger logger = LoggerFactory.getLogger(PaymentSummary.class);
    private Integer orderId;
    private Integer paymentId;
    private Integer customerId;
    private String createOrderRequest;
    private String createOrderResponse;
    private String callbackResponse;
    private String captureRequest;
    private String captureResponse;
    private String statusCode;
    private String status;
    private String responseMessage;


}
