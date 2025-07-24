package com.adobe.orderapp.service;

import com.adobe.orderapp.dto.OrderReport;
import com.adobe.orderapp.entity.LineItem;
import com.adobe.orderapp.entity.Order;
import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.repo.OrderRepo;
import com.adobe.orderapp.repo.ProductRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    // Constructor DI, no need for @Autowired
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;

    public List<Order> getOrders() {
        return  orderRepo.findAll();
    }

    public  List<OrderReport> getReport() {
        return  orderRepo.getReport();
    }

    public List<Order> getOrdersByDate(Date d) {
        return orderRepo.getOrderForGivenDate(d);
    }

    // Atomic - Unit of Work
    @Transactional
    public String placeOrder(Order order) throws EntityNotFoundException {
        double total = 0.0;
        // {"product": {"id": 3}, "qty": 2}
        List<LineItem> items = order.getItems();
        try {
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writeValueAsString(order));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for(LineItem item: items) {
            Product product =  getProductById(item.getProduct().getId());
            item.setAmount(product.getPrice() * item.getQty()); // discount, tax
            product.setQuantity(product.getQuantity() - item.getQty()); // DIRTY CHECKING --> UPDATE
            if(product.getQuantity() < 0) {
                throw new IllegalArgumentException("Product not in Stock!!!");
            }
            total += item.getAmount();
        }
        order.setTotal(total);
        orderRepo.save(order); // saves order and it's line items
        return  "Order placed!!!";
    }

    public Product addProduct(Product p) {
        return productRepo.save(p); // INSERT based on mapping
    }

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(int id) throws EntityNotFoundException {
        Optional<Product> opt = productRepo.findById(id);
        if(opt.isPresent()) {
            return  opt.get();
        }
        throw  new EntityNotFoundException("Product with id : " + id + " doesn't exist");
    }

    public long productCount() {
        return productRepo.count();
    }

    public List<Product> byRange(double low, double high) {
        return productRepo.findByPriceBetween(low, high);
    }

    @Transactional
    public Product modifyProductPrice(int id, double price) throws EntityNotFoundException{
        productRepo.updatePrice(id, price);
        return  getProductById(id);
    }
}
