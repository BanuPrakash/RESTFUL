package com.adobe.orderapp.api;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PatchExchange;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {
    private final OrderService service;

    // GET http://localhost:8080/api/products
    // Query Parameter
    // GET http://localhost:8080/api/products?low=5000&high=25000
    @GetMapping()
    public List<Product> getProducts(@RequestParam(name = "low", defaultValue = "0.0") double low,
                                     @RequestParam(name = "high", defaultValue = "0.0") double high ) {
        if(low == 0.0 && high == 0.0) {
            return service.getProducts();
        } else  {
            return service.byRange(low, high);
        }
    }

    // GET http://localhost:8080/api/products/2
    @GetMapping("/{pid}")
    public Product getProductById(@PathVariable("pid") int id) {
        return  service.getProductById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return  service.addProduct(product);
    }

    // PATCH http://localhost:8080/api/products/3?price=5350
    @PatchMapping("/{id}")
    public Product updateProductPrice(@PathVariable("id") int id, @RequestParam("price") double price) {
        return service.modifyProductPrice(id, price);
    }

    // PUT http://localhost:8080/api/products/3
    /*
        {
            "price": 5350,
            "quantity": 98
        }

     */
    @PutMapping("/{id}")
    public Product updateProductPricePut(@PathVariable("id") int id, @RequestBody Product p) {
        return service.modifyProductPrice(id, p.getPrice());
    }

    // DELETE http://localhost:8080/api/products/3
    // Accept: text/ plain
    @DeleteMapping("/{id}")
    public String doDelete(@PathVariable("id") int id) {
        //
        return  "Product deleted!!!";
    }
}
