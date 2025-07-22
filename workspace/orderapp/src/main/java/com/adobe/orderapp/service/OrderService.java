package com.adobe.orderapp.service;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    // Constructor DI, no need for @Autowired
    private final ProductRepo productRepo;

    public Product addProduct(Product p) {
        return productRepo.save(p); // INSERT based on mapping
    }

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(int id) {
        Optional<Product> opt = productRepo.findById(id);
        if(opt.isPresent()) {
            return  opt.get();
        }
        return null;
    }

    public long productCount() {
        return productRepo.count();
    }
}
