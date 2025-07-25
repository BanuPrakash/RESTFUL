package com.adobe.paymentservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payments")
@Slf4j
public class PaymentController {
    @Autowired
    PaymentService service;

    @PostMapping
    public String processPayment(@RequestBody OrderPayment payment) {
        System.out.println(payment + " recieved in PaymentController");
        service.processPayment(payment);
        return "Payment processing!!!";
    }


}
