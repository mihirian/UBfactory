package com.example.ubfactory.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarqueTextRequest
{
    private  Long id;
    private String marqueText;
    private Date startDate;
    private Date endDate;
    private String status;
}
