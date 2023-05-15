package com.example.ubfactory.exception;

public class BusinessException extends Exception {
    private int errorCode;
    private String errorMessage;


    public BusinessException(String message) {
        super();
        this.errorCode = 0;
        this.errorMessage = message;
    }

    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.errorMessage;
    }
}
