package com.example.ubfactory.objects;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class RazorpayRequestObject {
    public static final Logger logger = LoggerFactory.getLogger(RazorpayRequestObject.class);
    private Integer amount;

}
