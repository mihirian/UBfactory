package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.*;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.OrderHelper;
import com.example.ubfactory.helper.PaymentHelper;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.objects.OrderResponseObject;
import com.example.ubfactory.objects.RazorpayRequestObject;
import com.example.ubfactory.objects.RazorpayResponseObject;
import com.example.ubfactory.repository.*;
import com.example.ubfactory.service.RazorpayService;
import com.example.ubfactory.validator.OrderVaildator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private final String keyId = "rzp_test_XVEHHxt1YOPY7q";
    private final String secretKey = "szAIjTeJwbucWKaiTK3itpBY";
    @Autowired
    private ShippingRepository shippingRepository;
    @Autowired
    private PaymentHelper paymentHelper;
    @Autowired
    private PaymentRepository paymentRepository;

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
        // address shipping
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
        paymentSummary.setCreateOrderRequest(json.toString());
        RazorpayResponseObject responseObject = gson.fromJson(response, RazorpayResponseObject.class);
        paymentSummary.setCreateOrderResponse(responseObject.toString());
        paymentRepository.save(paymentSummary);
        OrderResponseObject orderResponseObject = orderHelper.getOrderResponse(responseObject,orderSummary);

        return orderResponseObject;
    }


}
