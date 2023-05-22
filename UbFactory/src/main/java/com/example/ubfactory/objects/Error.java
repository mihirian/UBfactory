package com.example.ubfactory.objects;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Data
public class Error {
    public static final Logger logger = LoggerFactory.getLogger(Error.class);
    private String code;
    private String description;
    private String source;
    private String step;
    private String reason;
    private Map<String, Object> metadata;
    private String field;
}
