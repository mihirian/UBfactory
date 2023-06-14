package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.*;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.helper.OrderHelper;
import com.example.ubfactory.helper.PaymentHelper;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.objects.OrderResponseObject;
import com.example.ubfactory.objects.RazorpayResponseObject;
import com.example.ubfactory.repository.*;
import com.example.ubfactory.service.InstaMojoService;
import com.example.ubfactory.validator.OrderVaildator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
@Service
public class InstaMojoServiceImpl implements InstaMojoService {
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
    private CustomerRepository customerRepository;
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

        if (orderRequestObject.getAmount().compareTo(totalPrice.get()) != 0) {
            throw new BusinessException("Requested amount and total amount is not equal.");
        }

        Shipping shipping = shippingRepository.findById(1).get();
        Customer customer = customerRepository.findById(orderRequestObject.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        OrderSummary orderSummary = orderHelper.createOrderSummary(cart, totalPrice.get(), shipping);
        PaymentSummary paymentSummary = paymentHelper.createPaymentSummary(orderSummary);
        int amount = paymentSummary.getAmount().multiply(new BigDecimal(100)).intValue();

        ApiContext context = ApiContext.create("test_MbsW2GTepwF7plQ9ZdhWX7NfBupRAB61Fyx", "test_LaA6mltdHvwRyDJCdrMXIsQIGIgWrr7GRqUGGiD7qBE9emkeL5KBqGGBneGBILQnuXf1MIYuiQoNpGhQbMUXo90XNmV6ubcnh4F2gf8Do9omeIBqrpwLxX5fnTL", ApiContext.Mode.TEST);
        Instamojo api = new InstamojoImpl(context);
        PaymentOrder order = new PaymentOrder();
        order.setName(customer.getFirstName()+" "+customer.getLastName());
        order.setEmail(customer.getEmail());
        order.setPhone(customer.getMobile());
        order.setCurrency("INR");
        order.setAmount((double) amount);
        order.setDescription("This is a test transaction.");
        order.setRedirectUrl("http://www.someexample.com");
        order.setWebhookUrl("http://www.someurl.com/");
        order.setTransactionId(String.valueOf(orderSummary.getId()));
        PaymentOrderResponse paymentOrderResponse = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            paymentOrderResponse = api.createPaymentOrder(order);
            System.out.println(paymentOrderResponse.getPaymentOrder().getStatus());

        } catch (HTTPException e) {
            System.out.println(e.getStatusCode());
            System.out.println(e.getMessage());
            System.out.println(e.getJsonPayload());

        } catch (ConnectionException e) {
            System.out.println(e.getMessage());
        }

        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("currency", paymentSummary.getCurrency());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), headers);

        paymentSummary.setCreateOrderRequest(json.toString());
        PaymentOrderResponse responseObject = gson.fromJson(json.toString(), PaymentOrderResponse.class);
        paymentSummary.setPaymentStatus(Status.PENDING.getStatus());
        paymentSummary.setCreateOrderResponse(gson.toJson(responseObject));
//        orderSummary.setRazorpayId(responseObject.getId());
        orderHelper.postCreateOrder(orderSummary);
        paymentRepository.save(paymentSummary);
        OrderResponseObject orderResponseObject = orderHelper.getOrderResponsemojo(responseObject, orderSummary);

        return orderResponseObject;
    }
}
