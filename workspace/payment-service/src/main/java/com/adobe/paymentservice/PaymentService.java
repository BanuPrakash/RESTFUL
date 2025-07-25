package com.adobe.paymentservice;


import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PaymentService {
    @Autowired
    RestTemplate restTemplate;

    @Observed(name = "payment:processPayment")
    public void processPayment(OrderPayment orderPayment) {
        log.info("Starting payment processing for Order ID: {}", orderPayment.getId());

        try {
            log.info("Validating payment details for Order ID: {}", orderPayment.getId());
            Thread.sleep(500);
            log.info("Payment details validated for Order ID: {}", orderPayment.getId());
            log.info("Authorizing payment for Order ID: {}", orderPayment.getId());
            Thread.sleep(500);
            log.info("Payment authorized for Order ID: {}", orderPayment.getId());
            log.info("Capturing payment for Order ID: {}", orderPayment.getId());
            Thread.sleep(500);
            log.info("Payment captured for Order ID: {}", orderPayment.getId());
            log.info("Completing payment processing for Order ID: {}", orderPayment.getId());
            Thread.sleep(500);
            log.info("Payment processing completed for Order ID: {}", orderPayment.getId());
            restTemplate.put("http://localhost:8081/api/order/" + orderPayment.getId(), "completed", String.class);
        } catch (InterruptedException e) {
            log.error("Thread was interrupted while processing payment for Order ID: {}", orderPayment.getId(), e);
        } catch (Exception e) {
            log.error("Exception occurred while processing payment for Order ID: {}", orderPayment.getId(), e);
        }
    }
}