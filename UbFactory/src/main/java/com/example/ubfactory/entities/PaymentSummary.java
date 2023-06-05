package com.example.ubfactory.entities;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "Payment_summary")
public class PaymentSummary extends BaseEntity {
    public static final Logger logger = LoggerFactory.getLogger(PaymentSummary.class);
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "payment_id")
    private String paymentId;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "create_order_request")
    private String createOrderRequest;
    @Column(name = "create_order_response")
    private String createOrderResponse;
    @Column(name = "callback_response")
    private String callbackResponse;
    @Column(name = "capture_request")
    private String captureRequest;
    @Column(name = "capture_response")
    private String captureResponse;
    @Column(name = "status_code")
    private String statusCode;
    @Column(name = "status")
    private String status;
    @Column(name = "response_message")
    private String responseMessage;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "currency")
    private String currency;


}
