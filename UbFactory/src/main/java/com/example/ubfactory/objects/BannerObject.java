package com.example.ubfactory.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BannerObject {
    public static final Logger logger = LoggerFactory.getLogger(BannerObject.class);
    private String name;
    private Date startDate;
    private Date endDate;
    private String url;
    private String status;
    private Date createdAt;
    private Date updateAt;



}
