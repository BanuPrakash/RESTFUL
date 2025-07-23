package com.adobe.orderapp.repo;

import com.adobe.orderapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {
}
