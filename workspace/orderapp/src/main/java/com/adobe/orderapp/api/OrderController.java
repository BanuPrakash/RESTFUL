package com.adobe.orderapp.api;

import com.adobe.orderapp.entity.Order;
import com.adobe.orderapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody  Order order) {
        return service.placeOrder(order);
    }


    // GET http://localhost:8080/api/orders
    // GET http://localhost:8080/api/orders?order-date=2025-07-24

    @GetMapping()
    public List<Order> getOrders(@RequestParam(name = "order-date", required = false)
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate) {
        if(orderDate != null) {
            return service.getOrdersByDate(orderDate);
        }
        return service.getOrders();
    }
}
