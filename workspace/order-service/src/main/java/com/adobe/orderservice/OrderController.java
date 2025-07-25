package com.adobe.orderservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        orderService.placeOrder(order);
        return ResponseEntity.ok("Order has been placed, will get back to you!!!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable("id") int id, @RequestBody String status) {
        orderService.updateOrder(id, status);
        return ResponseEntity.ok("Order has been updated");
    }
}
