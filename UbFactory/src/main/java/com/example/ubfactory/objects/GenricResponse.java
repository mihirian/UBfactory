package com.example.ubfactory.objects;

import com.example.ubfactory.enums.Status;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenricResponse<T> {
    public static ResponseEntity<Object> genricResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);
        return new ResponseEntity<Object>(map, status);
    }

    public Response<T> createSuccessResponse(T responseObject, Integer responseCode, String responseMessage){
        Response<T> response = new Response();
        if(responseObject != null) {
            response.setData(responseObject);
        }
        response.setStatus(ResponseConstants.SUCCESS);
        if(responseCode != null){
            response.setResponseCode(responseCode);
            response.setResponseMessage(responseMessage);
        }
        return  response;

    }

    public  Response<T> createErrorResponse(Integer errorCode,String errorMessage) {
        Response response = new Response();
        response.setResponseMessage(errorMessage);
        response.setResponseCode(errorCode);
        response.setStatus(ResponseConstants.FAILURE);
        return response;
    }

    public  Response<T> createErrorResponseWithPayload(Integer errorCode,T errorMessage) {
        Response response = new Response();
        response.setData(errorMessage);
        response.setResponseCode(errorCode);
        response.setStatus(ResponseConstants.FAILURE);
        return response;
    }
    public  Response<T> createErrorResponse(Integer errorCode,String errorMessage, Throwable th) {
        Response response = new Response();
        response.setResponseMessage(errorMessage);
        response.setResponseCode(errorCode);
        response.setStatus(ResponseConstants.FAILURE);
        response.setThrowable(th);

        return response;
    }

    public Response<T> createSuccessListResponse( List<T> responseObject ){
        Response<T> response = new Response();
        response.setListData(responseObject);
        response.setStatus(ResponseConstants.SUCCESS);
        return  response;

    }
}
