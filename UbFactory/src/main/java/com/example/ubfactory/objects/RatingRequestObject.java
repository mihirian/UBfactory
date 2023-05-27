package com.example.ubfactory.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingRequestObject {
    public static final Logger logger = LoggerFactory.getLogger(RatingRequestObject.class);
    private String remark;
    private Integer point;
    private Integer customerId;
    private Integer productId;
    private Date createdAt;
    private List<?> productRatingList;
    private String customerName;
    private String productAverageRating;

}
