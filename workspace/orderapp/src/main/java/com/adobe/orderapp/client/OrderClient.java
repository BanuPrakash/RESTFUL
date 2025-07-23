package com.adobe.orderapp.client;

import com.adobe.orderapp.entity.LineItem;
import com.adobe.orderapp.entity.Order;
import com.adobe.orderapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderClient implements CommandLineRunner {
    private final OrderService service;
    @Override
    public void run(String... args) throws Exception {
        List<Order> orders = service.getOrders();
        for(Order order : orders) {
            // select * from orders with LAZY
            System.out.println(order.getOrderDate() + ", " + order.getTotal());

            List<LineItem> items = order.getItems(); // proxy collection
            for(LineItem item : items) {
                // select * from items where order_fk = ? with LAZY
                System.out.println(item.getProduct().getName() + " : " + item.getQty() + " :  " + item.getQty());
            }
        }
    }
}
