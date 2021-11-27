package com.example.demo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class StartRequest {
    private String salt;
    private String merchant;
    private String orderRef;
    private String currency;
    private String customerEmail;
    private String language;
    private String sdkVersion;
    private List<String> methods;
    private Integer total;
    private String timeout;
    private Urls urls;
    private Invoice invoice;
}
