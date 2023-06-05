package com.example.ubfactory.helper;

import com.example.ubfactory.entities.*;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.objects.OrderResponseObject;
import com.example.ubfactory.objects.RazorpayResponseObject;
import com.example.ubfactory.repository.OrderSummaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class OrderHelper {
    public static final Logger logger = LoggerFactory.getLogger(OrderHelper.class);
    @Autowired
    private OrderSummaryRepository orderSummaryRepository;
    public void calculatePriceWithQuantity(CartItem cartItem, Optional<Product> product) {
    }

    public OrderSummary createOrderSummary(Cart cart, Shipping shipping) {
        OrderSummary orderSummary = new OrderSummary();
        orderSummary.setCustomer(cart.getCustomer());
//        orderSummary.setTotalPrice(totalPrice);
        orderSummary.setOrderStatus(Status.INITIATED.getStatus());
        orderSummary.setPaymentStatus(Status.INITIATED.getStatus());
        orderSummary.setCreatedAt(new Date());
        orderSummary.setUpdatedAt(new Date());
        orderSummary.setShipping(shipping);
        orderSummaryRepository.save(orderSummary);
        return orderSummary;
    }

    public OrderResponseObject getOrderResponse(RazorpayResponseObject responseObject, OrderSummary orderSummary) {
        OrderResponseObject orderResponseObject =new OrderResponseObject();
        orderResponseObject.setAmount(responseObject.getAmount());
        orderResponseObject.setOrderId(responseObject.getId());
        orderResponseObject.setEntity(responseObject.getEntity());
        orderResponseObject.setAttempts(responseObject.getAttempts());
        orderResponseObject.setReceipt(responseObject.getReceipt());
        orderResponseObject.setStatus(responseObject.getStatus());
        orderResponseObject.setAmount_due(responseObject.getAmount_due());
        orderResponseObject.setOffer_id(responseObject.getOffer_id());
        orderResponseObject.setCustomerId(orderSummary.getCustomer().getId());



       return orderResponseObject;
    }

    public void postCreateOrder(OrderSummary orderSummary) {
        orderSummary.setOrderStatus(Status.PENDING.getStatus());
        orderSummary.setPaymentStatus(Status.PENDING.getStatus());
        orderSummaryRepository.save(orderSummary);
    }
}
