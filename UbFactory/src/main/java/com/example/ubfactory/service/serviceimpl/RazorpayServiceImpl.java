package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.objects.RazorpayRequestObject;
import com.example.ubfactory.objects.RazorpayResponseObject;
import com.example.ubfactory.service.RazorpayService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RazorpayServiceImpl implements RazorpayService {
    public static final Logger logger = LoggerFactory.getLogger(RazorpayServiceImpl.class);

    private final String keyId = "rzp_test_XVEHHxt1YOPY7q";
    private final String secretKey = "szAIjTeJwbucWKaiTK3itpBY";

    @Override
    public RazorpayRequestObject createOrder(RazorpayRequestObject razorpayRequestObject) {
        RazorpayRequestObject requestObject = new RazorpayRequestObject();
        String url = "https://api.razorpay.com/v1/orders";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JSONObject json = new JSONObject();
        json.put("amount", razorpayRequestObject.getAmount());
        json.put("currency", razorpayRequestObject.getCurrency());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic cnpwX3Rlc3RfWFZFSEh4dDFZT1BZN3E6c3pBSWpUZUp3YnVjV0thaVRLM2l0cEJZ");
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString() + ", " + MediaType.TEXT_PLAIN.toString() + ", " + MediaType.TEXT_HTML.toString());

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), headers);
        String response = restTemplate.postForObject(url, httpEntity, String.class);

        System.out.println(response);

        RazorpayResponseObject responseObject = gson.fromJson(response, RazorpayResponseObject.class);
        requestObject.setAmount(responseObject.getAmount());
        requestObject.setOrderId(responseObject.getId());


        return requestObject;
    }
}
