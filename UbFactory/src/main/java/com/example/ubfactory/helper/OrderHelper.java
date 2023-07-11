package com.example.ubfactory.helper;

import com.example.ubfactory.entities.*;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.objects.ItemDetails;
import com.example.ubfactory.objects.OrderResponseObject;
import com.example.ubfactory.objects.RazorpayResponseObject;
import com.example.ubfactory.repository.OrderSummaryRepository;
import com.instamojo.wrapper.model.PaymentOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class OrderHelper {
    public static final Logger logger = LoggerFactory.getLogger(OrderHelper.class);
    @Autowired
    private OrderSummaryRepository orderSummaryRepository;
    @Autowired
     private JavaMailSender javaMailSender;
    public void calculatePriceWithQuantity(CartItem cartItem, Optional<Product> product) {
    }

    public OrderSummary createOrderSummary(Cart cart, AtomicReference<BigDecimal> totalPrice,Shipping shipping) {
        OrderSummary orderSummary = new OrderSummary();
        orderSummary.setCustomer(cart.getCustomer());
        orderSummary.setTotalPrice(totalPrice.get());
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
    public OrderResponseObject getOrderResponsemojo(PaymentOrderResponse responseObject, OrderSummary orderSummary ){
        OrderResponseObject orderResponseObject = new OrderResponseObject();
//        orderResponseObject.setAmount(responseObject.getAmount());
//        orderResponseObject.setOrderId(responseObject.getId());
//        orderResponseObject.setEntity(responseObject.getEntity());
//        orderResponseObject.setAttempts(responseObject.getAttempts());
//        orderResponseObject.setReceipt(responseObject.getReceipt());
        orderResponseObject.setStatus(String.valueOf(responseObject.getPaymentOrder().getStatus()));
//        orderResponseObject.setAmount_due(responseObject.getAmount_due());
//        orderResponseObject.setOffer_id(responseObject.getOffer_id());
        orderResponseObject.setCustomerId(orderSummary.getCustomer().getId());
        orderResponseObject.setUrl(responseObject.getPaymentOptions().getPaymentUrl());


        return orderResponseObject;
    }

    public void postCreateOrder(OrderSummary orderSummary) {
        orderSummary.setOrderStatus(Status.PENDING.getStatus());
        orderSummary.setPaymentStatus(Status.PENDING.getStatus());
        orderSummaryRepository.save(orderSummary);
    }

    public OrderResponseObject getOrderResponseCashOnDelevery(List<CartItem> cartItem, OrderSummary orderSummary)
    {
        OrderResponseObject orderResponseObject = new OrderResponseObject();
        orderResponseObject.setAmount(orderSummary.getTotalPrice().intValue());


//        orderResponseObject.setOrderId(responseObject.getId());
//        orderResponseObject.setEntity(responseObject.getEntity());
        return orderResponseObject;
    }
    public void sendOrderDetailsEmail(Customer customer, List<String> recipientEmail, List<ItemDetails> itemDetailsList, BigDecimal totalAmount)
    {

        SimpleMailMessage message = new SimpleMailMessage();
        recipientEmail.stream().forEach((e) ->{
           message.setTo(e);
        });
        message.setSubject("Order Confirmation - Order Details");

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Dear Customer,\n\n");
        emailContent.append("Thank you for your order. We are pleased to confirm your purchase. Below are the details of your order:\n\n");
        emailContent.append("Delivery Address:\n");
        emailContent.append("Address: ").append(customer.getAddresses()).append("\n");
        emailContent.append("City: ").append(customer.getTown()).append("\n");
        emailContent.append("State: ").append(customer.getState()).append("\n");
        emailContent.append("Postal Code: ").append(customer.getPinCode()).append("\n\n");
        emailContent.append("Street address").append(customer.getStreetAddress()).append("\n\n");
        emailContent.append("Total Items: ").append(itemDetailsList.size()).append("\n\n");

        for (ItemDetails itemDetails : itemDetailsList) {
            emailContent.append("Product: ").append(itemDetails.getName()).append("\n");
            emailContent.append("Quantity: ").append(itemDetails.getQuantity()).append("\n\n");
        }

        emailContent.append("Total Amount: $").append(totalAmount).append("\n\n");
        emailContent.append("Thank you for choosing our service. If you have any further questions or concerns, please feel free to contact our customer support team.\n\n");
        emailContent.append("Best regards,\n");
        emailContent.append("Your Company Name" );

        message.setText(emailContent.toString());

        javaMailSender.send(message);

    }

}
