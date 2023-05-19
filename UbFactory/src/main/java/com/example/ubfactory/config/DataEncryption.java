package com.example.ubfactory.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class DataEncryption {

    public static final Logger logger = LoggerFactory.getLogger(DataEncryption.class);
     public String decoder(String encodedString){
         byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
         String decodedString = new String(decodedBytes);
         return decodedString;
     }

     public String encoder(String apiKey , String secretKey){
         String concatenatedKeys = apiKey + ":" + secretKey;
         byte[] encodedBytes = Base64.getEncoder().encode(concatenatedKeys.getBytes());
         String encodedString = new String(encodedBytes);
         return encodedString;
     }


    public static void main(String[] args) {
        String encodedString = "cnpwX3Rlc3RfWFZFSEh4dDFZT1BZN3E6c3pBSWpUZUp3YnVjV0thaVRLM2l0cEJZ";

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);

        System.out.println(decodedString);
    }



}
