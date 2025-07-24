package com.adobe.orderapp.api;

import com.adobe.orderapp.aop.Tx;
import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.EntityNotFoundException;
import com.adobe.orderapp.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Tx
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
    public Product getProductById(@PathVariable("pid") int id) throws EntityNotFoundException{
        return  service.getProductById(id);
    }

    // GET http://localhost:8080/api/products/cache/2
    @Cacheable(value = "productCache", key = "#id")
    @GetMapping("/cache/{pid}")
    public Product getProductByCacheId(@PathVariable("pid") int id) throws EntityNotFoundException{
        System.out.println("Cache Miss!!!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return  service.getProductById(id);
    }



    // GET http://localhost:8080/api/products/etag/2
    @GetMapping("/etag/{pid}")
    public ResponseEntity<Product> getProductByEtagId(@PathVariable("pid") int id) throws EntityNotFoundException{
        Product p  =  service.getProductById(id);
        // can use Version instead of hashCode
        return ResponseEntity.ok().eTag(String.valueOf(p.hashCode())).body(p);
    }



    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody @Valid Product product) {
        return  service.addProduct(product);
    }

    // PATCH http://localhost:8080/api/products/3?price=5350
    @CachePut(value = "productCache", key = "#id")
    @PatchMapping("/{id}")
    public Product updateProductPrice(@PathVariable("id") int id, @RequestParam("price") double price) throws EntityNotFoundException {
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
    public Product updateProductPricePut(@PathVariable("id") int id, @RequestBody Product p) throws EntityNotFoundException{
        return service.modifyProductPrice(id, p.getPrice());
    }

    // DELETE http://localhost:8080/api/products/3
    // Accept: text/ plain
    @CacheEvict(value = "productCache", key = "#id")
    @DeleteMapping("/{id}")
    public String doDelete(@PathVariable("id") int id) {
        //
        return  "Product deleted!!!";
    }
}
