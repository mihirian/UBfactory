package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.*;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.OrderHelper;
import com.example.ubfactory.helper.PaymentHelper;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.repository.*;
import com.example.ubfactory.service.RazorpayService;
import com.example.ubfactory.validator.OrderVaildator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class RazorpayServiceImpl implements RazorpayService {
    public static final Logger logger = LoggerFactory.getLogger(RazorpayServiceImpl.class);

    @Autowired
    private Environment environment;
    @Autowired
    private OrderVaildator orderVaildator;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderHelper orderHelper;
    @Autowired
    private ShippingRepository shippingRepository;
    @Autowired
    private PaymentHelper paymentHelper;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderSummaryRepository orderSummaryRepository;

    @Override
    public OrderResponseObject createOrder(OrderRequestObject orderRequestObject) throws BusinessException {
        Cart cart = orderVaildator.validateOrderRequest(orderRequestObject);
        List<CartItem> cartItem = cartItemRepository.findAllByCart(cart);
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<BigDecimal>(new BigDecimal(0.00));
        cartItem.forEach((ele) -> {
            Product product = ele.getProduct();
            BigDecimal price = product.getPrice();
            Integer quantity = ele.getQuantity();
            BigDecimal currentProdcutTotalPrice = price.multiply(new BigDecimal(quantity));
            totalPrice.updateAndGet(v -> v.add(currentProdcutTotalPrice));
        });
        if(orderRequestObject.getAmount().compareTo(totalPrice.get()) !=0){
           throw new BusinessException("Requested amount and total amount is not equal.");
        }
        // address shipping pending
        Shipping shipping = shippingRepository.findById(1).get();
        OrderSummary orderSummary = orderHelper.createOrderSummary(cart,totalPrice.get(), shipping);
        PaymentSummary paymentSummary = paymentHelper.createPaymentSummary(orderSummary);
         int amount = paymentSummary.getAmount().multiply(new BigDecimal(100)).intValue();

        String url = "https://api.razorpay.com/v1/orders";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("currency", paymentSummary.getCurrency());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic cnpwX3Rlc3RfWFZFSEh4dDFZT1BZN3E6c3pBSWpUZUp3YnVjV0thaVRLM2l0cEJZ");
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), headers);
        String response = restTemplate.postForObject(url, httpEntity, String.class);
        //response check
        paymentSummary.setCreateOrderRequest(json.toString());
        RazorpayResponseObject responseObject = gson.fromJson(response, RazorpayResponseObject.class);
        paymentSummary.setPaymentStatus(Status.PENDING.getStatus());
        paymentSummary.setCreateOrderResponse(responseObject.toString());
        orderSummary.setRazorpayId(responseObject.getId());
        orderHelper.postCreateOrder(orderSummary);
        paymentRepository.save(paymentSummary);
        OrderResponseObject orderResponseObject = orderHelper.getOrderResponse(responseObject,orderSummary);

        return orderResponseObject;
    }

    @Override
    public CapturePaymentResponse capturePayment(OrderRequestObject orderRequestObject) throws BusinessException, RazorpayException {
        paymentHelper.validateCapturePayment(orderRequestObject);
        OrderSummary orderSummary = orderSummaryRepository.findByRazorpayId(orderRequestObject.getRazorpayId());
        if(orderSummary == null){
            throw new BusinessException("Order detail not found");
        }
        PaymentSummary paymentSummary = paymentRepository.findByOrderId(orderSummary.getId());
        if(paymentSummary == null){
            throw new BusinessException("Payment detail not found");
        }
        paymentSummary.setPaymentId(orderRequestObject.getPaymentId());

        String url = "https://api.razorpay.com/v1/payments/"+orderRequestObject.getPaymentId()+"/capture";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JSONObject json = new JSONObject();
        json.put("amount", orderRequestObject.getAmount());
        json.put("currency", "INR");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic cnpwX3Rlc3RfWFZFSEh4dDFZT1BZN3E6c3pBSWpUZUp3YnVjV0thaVRLM2l0cEJZ");
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), headers);
        String response = restTemplate.postForObject(url, httpEntity, String.class);
        paymentSummary.setCaptureRequest(json.toString());
        CapturePaymentResponse capturePaymentResponse = gson.fromJson(response, CapturePaymentResponse.class);
        paymentSummary.setCaptureResponse(capturePaymentResponse.toString());
        if(capturePaymentResponse.getStatus().equalsIgnoreCase("captured")){
            paymentSummary.setPaymentStatus(Status.SUCCESS.getStatus());
            orderSummary.setOrderStatus(Status.SUCCESS.getStatus());
            orderSummary.setPaymentStatus(Status.SUCCESS.getStatus());
        }
        else if(capturePaymentResponse.getError() != null || capturePaymentResponse.getError_code() != null){
            paymentSummary.setPaymentStatus(Status.FAILURE.getStatus());
            orderSummary.setOrderStatus(Status.FAILURE.getStatus());
            orderSummary.setPaymentStatus(Status.FAILURE.getStatus());
        }else {
            paymentSummary.setPaymentStatus(Status.PENDING.getStatus());
            orderSummary.setOrderStatus(Status.PENDING.getStatus());
            orderSummary.setPaymentStatus(Status.PENDING.getStatus());
        }

        return capturePaymentResponse;
    }


}
