package com.adobe.orderapp.dto;

import java.util.Date;
// constructor + getters -- no setters for immutable objects
public record OrderReport(String firstName, String lastName, double total, Date orderDate) {
}
