package com.example.ubfactory.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateOrderObject
{
  private int orderid;
  private String orderStatus;
  private String paymentStatus;
  private String refundStatus;
  private Date UpdatedAt;
  private String message;


}
