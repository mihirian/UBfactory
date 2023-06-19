package com.example.ubfactory.controller;

//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.objects.CapturePaymentResponse;
import com.example.ubfactory.objects.GenericResponse;
import com.example.ubfactory.objects.OrderRequestObject;
import com.example.ubfactory.objects.OrderResponseObject;
import com.example.ubfactory.service.InstaMojoService;
import com.example.ubfactory.service.RazorpayService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/razorpay")
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private RazorpayService razorpayService;
    @Autowired
    private InstaMojoService instaMojoService;

    @PostMapping("/create-order")

    //create order
     public Response<?> createOrder(@RequestBody OrderRequestObject orderRequestObject) {
        GenericResponse<OrderResponseObject> response = new GenericResponse<>();
        try {
            OrderResponseObject requestObject = razorpayService.createOrder(orderRequestObject);
            return response.createSuccessResponse(requestObject, HttpStatus.CREATED.value(), ResponseConstants.ORDER_CREATED_SUCCESSFULLY);
        }catch (BusinessException b){
            return response.createErrorResponse(b.getErrorCode(),b.getMessage());
         } catch (Exception e) {
            return response.createErrorResponse(401,ResponseConstants.REQUEST_TIME_OUT);
         }
     }

     @PostMapping("/capture/payment")
    public Response<?> capturePayment(@RequestBody OrderRequestObject orderRequestObject) {
         GenericResponse<CapturePaymentResponse> response = new GenericResponse<>();
         try {
            CapturePaymentResponse requestObject = razorpayService.capturePayment(orderRequestObject);
             return response.createSuccessResponse(requestObject, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        } catch (BusinessException b){
             return response.createErrorResponse(b.getErrorCode(),b.getMessage());
         } catch (Exception e) {
             return response.createErrorResponse(401,ResponseConstants.REQUEST_TIME_OUT);
         }
    }

    @GetMapping("billgenrater/{id}")
    public Response<?> billGenrater(@PathVariable int id) throws BusinessException {
        GenericResponse<Response> response = new GenericResponse<>();
        try {
            Response response1 = razorpayService.billGenrater(id);
            return response.createSuccessResponse(response1, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        } catch (Exception e) {
            return response.createErrorResponse(401,ResponseConstants.REQUEST_TIME_OUT);
        }
    }

    @PostMapping("ordersearch")
    public Response<?> orderHistory(@RequestBody OrderRequestObject orderRequestObject) {
        GenericResponse<OrderHistoryResponse> response = new GenericResponse<>();
        try {
            OrderHistoryResponse requestObject = razorpayService.orderhistory(orderRequestObject);
            return response.createSuccessResponse(requestObject, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        } catch (BusinessException b){
            return response.createErrorResponse(b.getErrorCode(),b.getMessage());
        } catch (Exception e) {
            return response.createErrorResponse(401,ResponseConstants.REQUEST_TIME_OUT);
        }
    }
    @PostMapping("/instamojo/create-order")

    //create order
    public ResponseEntity<?> createPaymentOrder(@RequestBody OrderRequestObject orderRequestObject) {
        try {
            OrderResponseObject requestObject = instaMojoService.createOrder(orderRequestObject);
            return GenericResponse.genericResponse("Success", HttpStatus.CREATED, requestObject);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    @PostMapping("/instamojo/capture-order")
    public ResponseEntity<?> capturePaymentInstaMojo(HttpServletRequest httpRequest,@ModelAttribute InstaMojoCallBackRequest orderRequestObject) {
        try {
            String requestBody = httpRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println("Request body: " + requestBody);
            System.out.println(" body: " + orderRequestObject);
            logger.info("Request body: " + requestBody);
            logger.info(" body: " + orderRequestObject);

//            InstaMojoCallBackRequest orderRequestObject = new InstaMojoCallBackRequest();
//            if(amount != null) orderRequestObject.setAmount(amount);
//            if(buyer != null) orderRequestObject.setBuyer(buyer);
//            if(buyer_name != null) orderRequestObject.setBuyer_name(buyer_name);
//            if(buyer_phone != null) orderRequestObject.setBuyer_phone(buyer_phone);
//            if(currency != null) orderRequestObject.setCurrency(currency);
//            if(fees != null) orderRequestObject.setFees(fees);
//            if(longurl != null) orderRequestObject.setLongurl(longurl);
//            if(mac != null) orderRequestObject.setMac(mac);
//            if(payment_id != null) orderRequestObject.setPayment_id(payment_id);
//            if(payment_request_id != null) orderRequestObject.setPayment_request_id(payment_request_id);
//            if(purpose != null) orderRequestObject.setPurpose(purpose);
//            if(shorturl != null) orderRequestObject.setShorturl(shorturl);
//            if(status != null) orderRequestObject.setStatus(status);

            CapturePaymentResponse requestObject = instaMojoService.capturePayment(orderRequestObject);
            return GenericResponse.genericResponse("Success", HttpStatus.CREATED, requestObject);
        } catch (Exception e) {
            System.err.println("Error while processing request: " + e.getMessage());
            e.printStackTrace();
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }



}


