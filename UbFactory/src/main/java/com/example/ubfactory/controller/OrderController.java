package com.example.ubfactory.controller;

//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;

import com.example.ubfactory.entities.Customer;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        } catch (BusinessException b) {
            return response.createErrorResponse(b.getErrorCode(), b.getMessage());
        } catch (Exception e) {
            return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
        }
    }

    @PostMapping("/capture/payment")
    public Response<?> capturePayment(@RequestBody OrderRequestObject orderRequestObject) {
        GenericResponse<CapturePaymentResponse> response = new GenericResponse<>();
        try {
            CapturePaymentResponse requestObject = razorpayService.capturePayment(orderRequestObject);
            return response.createSuccessResponse(requestObject, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        } catch (BusinessException b) {
            return response.createErrorResponse(b.getErrorCode(), b.getMessage());
        } catch (Exception e) {
            return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
        }
    }

    @GetMapping("billgenrater/{id}")
    public Response<?> billGenrater(@PathVariable int id) throws BusinessException {
        GenericResponse<Response> response = new GenericResponse<>();
        try {
            Response response1 = razorpayService.billGenrater(id);
            return response.createSuccessResponse(response1, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        } catch (Exception e) {
            return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
        }
    }

    @PostMapping("ordersearch")
    public Response<?> orderHistory(@RequestBody OrderRequestObject orderRequestObject) {
        GenericResponse<OrderHistoryResponse> response = new GenericResponse<>();
        try {
            OrderHistoryResponse requestObject = razorpayService.orderhistory(orderRequestObject);
            return response.createSuccessResponse(requestObject, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        } catch (BusinessException b) {
            return response.createErrorResponse(b.getErrorCode(), b.getMessage());
        } catch (Exception e) {
            return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
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
    //create order
    public ResponseEntity<?> capturePaymentInstaMojo(@RequestBody InstaMojoCallBackRequest orderRequestObject) {
        try {
            CapturePaymentResponse requestObject = instaMojoService.capturePayment(orderRequestObject);
            return GenericResponse.genericResponse("Success", HttpStatus.CREATED, requestObject);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //update order byId

    @PostMapping("/update/order")
    public ResponseEntity<Object> updateOrderSummeryById(@RequestBody UpdateOrderObject updateOrderObject) throws BusinessException {
        GenericResponse<UpdateOrderObject> response = new GenericResponse<>();
        try {
            UpdateOrderObject response1 = instaMojoService.updateOrderSummery(updateOrderObject);
            return GenericResponse.genericResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, response1);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/search/order/Bydate")
    public ResponseEntity<Object> searchDataByDate(@RequestBody SearchOrderObjectByDate searchOrderObjectByDate) throws BusinessException {
        GenericResponse<SearchOrderObjectByDate> response = new GenericResponse<>();
        try {
            int page = searchOrderObjectByDate.getPage();
            int pageSize = searchOrderObjectByDate.getPageSize();
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<SearchOrderObjectByDate> responsePage = instaMojoService.searchDateByDate(searchOrderObjectByDate, pageable);
            List<SearchOrderObjectByDate> responseData = responsePage.getContent();
            return GenericResponse.genericResponse(Status.SUCCESS.getStatus(), HttpStatus.OK, responseData);
        } catch (Exception e) {
            return GenericResponse.genericResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }


    }
}


