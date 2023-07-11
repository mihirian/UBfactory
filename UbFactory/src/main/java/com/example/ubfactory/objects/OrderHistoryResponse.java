package com.example.ubfactory.objects;

import com.example.ubfactory.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderHistoryResponse {
    public static final Logger logger = LoggerFactory.getLogger(OrderHistoryResponse.class);
    private Integer customerId;
    private List<ProductObject> products;
    private String orderStatus;
    private String paymentStatus;
    private BigDecimal orderAmount;
    private List<?> orderHistoryItems;
    private Integer orderId;
    private Date createdAt;

}
