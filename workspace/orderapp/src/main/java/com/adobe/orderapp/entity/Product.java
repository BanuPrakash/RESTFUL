package com.adobe.orderapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @NotBlank(message = "Name is required!!!")
    @NotBlank(message = "{product.name.required}")
    @Column(name="name", length = 100)
    private String name;

//    @Min(value = 10, message = "Price ${validatedValue} should be more than {value}")
    @Min(value = 10, message = "{product.price.min}")
    private double price;

    @Min(value = 1, message = "Quantity ${validatedValue} should be more than {value}")
    @Column(name="qty")
    private int quantity;
}
