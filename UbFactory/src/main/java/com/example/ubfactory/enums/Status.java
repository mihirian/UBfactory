package com.example.ubfactory.enums;

public enum Status {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),
    PENDING("PENDING"),
    INITIATED("INITIATED");


    private String status;

    Status(String status) {
        this.status = status;
    }

    public static Status fromString(String status) {
        for (Status value : values()) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
