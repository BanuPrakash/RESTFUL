package com.adobe.orderapp.repo;

import com.adobe.orderapp.dto.OrderReport;
import com.adobe.orderapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer> {


//    @Query("select c.firstName, c.lastName, o.total from Order o inner join o.customer c")
//    List<Object[]> getReport();

    @Query("select new com.adobe.orderapp.dto.OrderReport(c.firstName, c.lastName, o.total, o.orderDate) " +
            " from Order o inner  join  o.customer c")
    List<OrderReport> getReport();

    @Query("from Order where DATE(orderDate) = :od")
    List<Order> getOrderForGivenDate(@Param("od") Date orderDate);

}
