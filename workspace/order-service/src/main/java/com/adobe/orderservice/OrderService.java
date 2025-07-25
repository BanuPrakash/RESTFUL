package com.adobe.orderservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrderService {
    @Autowired
    RestTemplate restTemplate;

    public void placeOrder(Order order) {
        log.info("Placing order : order id {}", order.getId());
        log.info("Payment : {} " , order.getPayment());
        OrderPayment payment = order.getPayment();
        restTemplate.postForEntity("http://localhost:8082/api/payments",
                    payment,String.class);
        persistOrder(order);
    }

    public void updateOrder(int id, String status) {
        log.info("Updating order status : order id {}", id + " to " + status);
    }


    private void persistOrder(Order order) {
        log.info("Persisting order details for order : order id {}", order.getId());
    }

}
