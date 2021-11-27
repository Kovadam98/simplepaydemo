package com.example.demo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Invoice {
    String name;
    String company;
    String country;
    String state;
    String city;
    String zip;
    String address;
    String address2;
    String phone;
}
