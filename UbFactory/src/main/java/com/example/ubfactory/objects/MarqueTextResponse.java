package com.example.ubfactory.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarqueTextResponse {
    private Long id;
    private String Status;

    private String marqueeText;
    private String marqueeName;

}
