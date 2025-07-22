package com.adobe.orderapp.client;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductClient implements CommandLineRunner {
    private final OrderService service;

    @Override
    public void run(String... args) throws Exception {
        addProducts();
        printProducts();
    }

    private void printProducts() {
        List<Product> products = service.getProducts();
        for(Product p : products) {
            System.out.println(p);
        }
    }

    private void addProducts() {
        if(service.productCount() == 0) {
            Product p1 = Product.builder().name("iPhone 16").price(89000.00).quantity(100).build();
            Product p2 = Product.builder().name("Sony Bravia").price(2_97_000.00).quantity(100).build();
            Product p3 = Product.builder().name("Wacom").price(4500).quantity(100).build();
            Product p4 = Product.builder().name("Logitech Mouse").price(900.00).quantity(100).build();
            service.addProduct(p1);
            service.addProduct(p2);
            service.addProduct(p3);
            service.addProduct(p4);
        }
    }
}
