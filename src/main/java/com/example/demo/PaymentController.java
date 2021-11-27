package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class PaymentController {

    @GetMapping("/success")
    public String success() {
        return "Success";
    }

    @GetMapping("/fail")
    public String fail() {
        return "Fail";
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "Cancel";
    }

    @GetMapping("/timeout")
    public String timeout() {
        return "Timeout";
    }

    @PostMapping
    public void post(String message) {
        log.info("{}", message);
    }
}
