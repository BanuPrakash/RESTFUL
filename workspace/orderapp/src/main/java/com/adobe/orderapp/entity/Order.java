package com.adobe.orderapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="order_date")
    private Date orderDate = new Date();

    @ManyToOne
    @JoinColumn(name="customer_fk")
    private Customer customer; // order is be a customer

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="order_fk")
    private List<LineItem> items = new ArrayList<>(); // order has many items

    private double total;
}
