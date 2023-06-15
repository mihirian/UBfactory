package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.*;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.OrderHelper;
import com.example.ubfactory.helper.PaymentHelper;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.repository.*;
import com.example.ubfactory.service.RazorpayService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import com.example.ubfactory.validator.OrderVaildator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    private CartRepository cartRepository;
    @Autowired
    private OrderSummaryRepository orderSummaryRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
  private OrderItemRepository orderItemRepository;

    @Override
    public OrderResponseObject createOrder(OrderRequestObject orderRequestObject) throws BusinessException {
        Cart cart = orderVaildator.validateOrderRequest(orderRequestObject);
        List<CartItem> cartItem = cartItemRepository.findAllByCart(cart);
        Shipping shipping = shippingRepository.findById(1).get();
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<BigDecimal>(new BigDecimal(0.00));
        OrderSummary orderSummary = orderHelper.createOrderSummary(cart,totalPrice, shipping);

        List<OrderItem> orderItemList =new ArrayList<>();
        cartItem.forEach((ele) -> {
            Product product = ele.getProduct();
            BigDecimal price = product.getOriginalPrice();
            Integer quantity = ele.getQuantity();
            OrderItem orderItem =new OrderItem();
            orderItem.setPrice(price);
            orderItem.setQuantity(quantity);
            orderItem.setCreatedAt(new Date());
            orderItem.setUpdatedAt(new Date());
            orderItem.setProduct(product);
            orderItem.setOrderSummary(orderSummary);
            orderItemList.add(orderItem);
            BigDecimal currentProdcutTotalPrice = price.multiply(new BigDecimal(quantity));
            totalPrice.updateAndGet(v -> v.add(currentProdcutTotalPrice));
        });
        if (orderRequestObject.getAmount().compareTo(totalPrice.get()) != 0) {
            throw new BusinessException("Requested amount and total amount is not equal.");
        }
        // address shipping pending
        orderItemRepository.saveAll(orderItemList);
        orderSummary.setTotalPrice(totalPrice.get());
        orderSummaryRepository.save(orderSummary);
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
        OrderResponseObject orderResponseObject = orderHelper.getOrderResponse(responseObject, orderSummary);

        return orderResponseObject;
    }

    @Override
    public CapturePaymentResponse capturePayment(OrderRequestObject orderRequestObject) throws BusinessException, RazorpayException {
        paymentHelper.validateCapturePayment(orderRequestObject);
        OrderSummary orderSummary = orderSummaryRepository.findByRazorpayId(orderRequestObject.getRazorpayId());
        if (orderSummary == null) {
            throw new BusinessException("Order detail not found");
        }
        PaymentSummary paymentSummary = paymentRepository.findByOrderId(orderSummary.getId());
        if (paymentSummary == null) {
            throw new BusinessException("Payment detail not found");
        }
        paymentSummary.setPaymentId(orderRequestObject.getPaymentId());
        paymentRepository.save(paymentSummary);
        int amount = orderRequestObject.getAmount().multiply(new BigDecimal(100)).intValue();
        String url = "https://api.razorpay.com/v1/payments/" + orderRequestObject.getPaymentId() + "/capture";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JSONObject json = new JSONObject();
        json.put("amount", amount);
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
        if (capturePaymentResponse.getStatus().equalsIgnoreCase("captured")) {
            paymentSummary.setPaymentStatus(Status.SUCCESS.getStatus());
            orderSummary.setOrderStatus(Status.SUCCESS.getStatus());
            orderSummary.setPaymentStatus(Status.SUCCESS.getStatus());
            paymentRepository.save(paymentSummary);
            orderSummaryRepository.save(orderSummary);
        } else if (capturePaymentResponse.getError() != null || capturePaymentResponse.getError_code() != null) {
            paymentSummary.setPaymentStatus(Status.FAILURE.getStatus());
            orderSummary.setOrderStatus(Status.FAILURE.getStatus());
            orderSummary.setPaymentStatus(Status.FAILURE.getStatus());
            paymentRepository.save(paymentSummary);
            orderSummaryRepository.save(orderSummary);
        } else {
            paymentSummary.setPaymentStatus(Status.PENDING.getStatus());
            orderSummary.setOrderStatus(Status.PENDING.getStatus());
            orderSummary.setPaymentStatus(Status.PENDING.getStatus());
            paymentRepository.save(paymentSummary);
            orderSummaryRepository.save(orderSummary);
        }

        return capturePaymentResponse;
    }

    @Override
    public Response billGenrater(int id) {
        GenericResponse<SheepingResponse> response = new GenericResponse<>();
        SheepingResponse response1 = new SheepingResponse();
        Cart cart = cartRepository.findByCustomerId(id);
        List<CartItem> cartList = cartItemRepository.findByCartId(id);
        BigDecimal subprice = BigDecimal.ZERO;

        for (CartItem item : cartList) {
            BigDecimal price = item.getProduct().getPrice();
            subprice = subprice.add(price);
        }
        System.out.println(subprice);
        int sheepingId = 1;
        Optional<Shipping> shipping = shippingRepository.findById(sheepingId);
        BigDecimal sheepingprice = shipping.get().getCharges();
        BigDecimal totalPrice = subprice.add(sheepingprice);
        response1.setSubPrice(subprice);
        response1.setTotalPrice(totalPrice);
        response1.setSheepingprice(sheepingprice);
        return response.createSuccessResponse(response1, HttpStatus.OK.value(), ResponseConstants.SUCCESS);

    }

    @Override


    public OrderHistoryResponse orderhistory(OrderRequestObject orderRequestObject) throws BusinessException {
       OrderHistoryResponse historyResponse = new OrderHistoryResponse();
        if (orderRequestObject.getOwnerType() != "ADMIN" && orderRequestObject.getCustomerId() != 0) {
//            Customer customer = customerRepository.findById(orderRequestObject.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
//            Cart cart = cartRepository.findByCustomer(customer).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
//            List<CartItem> cartItems = cartItemRepository.findAllByCart(cart);
            List<OrderSummary> orderSummary = orderSummaryRepository.findAllByCustomerId(orderRequestObject.getCustomerId());
            if (orderSummary == null) {
                throw new BusinessException("Order history not found");
            }
//            OrderHistoryResponse historyItem = new OrderHistoryResponse();
            List<OrderHistoryResponse> orderHistoryItems = new ArrayList<>();
            for (OrderSummary orderSummary1 : orderSummary) {
                OrderHistoryResponse historyItem = new OrderHistoryResponse();
                historyItem.setPaymentStatus(orderSummary1.getPaymentStatus());
                historyItem.setOrderStatus(orderSummary1.getOrderStatus());
                historyItem.setOrderAmount(orderSummary1.getTotalPrice());
                historyItem.setCustomerId(orderSummary1.getCustomer().getId());
                historyItem.setOrderId(orderSummary1.getId());
                // Get product details for each order summary
                List<OrderItem> orderItemList = orderItemRepository.findAllByOrderSummary(orderSummary1);
                List<ProductObject> orderProducts = new ArrayList<>();
                for (OrderItem orderItem : orderItemList) {
                    ProductObject orderProduct = new ProductObject();
                        orderProduct.setName(orderItem.getProduct().getName());
                        orderProduct.setStockQuantity(orderItem.getQuantity());
                        orderProduct.setPrice(orderItem.getProduct().getOriginalPrice());
                        orderProduct.setImageUrl(orderItem.getProduct().getImageUrl());
                        orderProduct.setProductId(orderItem.getProduct().getId());
                        orderProducts.add(orderProduct);
                }

                historyItem.setProducts(orderProducts);
                orderHistoryItems.add(historyItem);
            }
//              orderHistoryItems.add(historyItem);
            historyResponse.setOrderHistoryItems(orderHistoryItems);
        }

        return historyResponse;
    }

}