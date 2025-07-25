package com.adobe.orderservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    int id;
    Date orderDate = new Date();
    String status = "pending";

    OrderPayment payment = new OrderPayment();
}
