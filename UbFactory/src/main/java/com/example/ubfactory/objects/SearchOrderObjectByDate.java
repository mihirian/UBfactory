package com.example.ubfactory.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchOrderObjectByDate
{
    private String startDate;
    private String endDate;
    private String paymentStatus;
    private String orderStatus;
    private BigDecimal paymentAmount;
    private int orderId;
    private String refundStatus;
    private int page;
    private int pageSize;
    private String DateAndTime;

    private String sortBy;



}
