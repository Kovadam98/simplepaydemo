package com.example.demo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Urls {
    private String success;
    private String fail;
    private String cancel;
    private String timeout;
}
