package com.adobe.datarest;

import com.adobe.datarest.entity.Product;
import com.adobe.datarest.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@BasePathAwareController("/products")
public class ProductController {
    @Autowired
    private   ProductRepo productRepo;

    // http:localhost:8080/products
    @GetMapping()
    public @ResponseBody  List<Product> getProducts() {
        // use WebMvcLinkBuilder
        // List<Product> products =  productRepo.findAll();
        return Arrays.asList(
                new Product(14, "A", 35345, 12),
                new Product(36, "B", 35345, 12)
        );
    }

}
