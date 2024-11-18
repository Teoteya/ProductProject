package com.example.product_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.product_project.service.ProductService;
import com.example.product_project.entity.Product;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAll() {     // получение списка продуктов
        return productService.getAll();
    }

    @GetMapping(value = "/get/list/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getByProductId(@PathVariable String productId) {   // получение списка продуктов по productId
        return productService.getByProductId(productId);
    }

    @GetMapping(value = "/get/one/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable Long id) {   // получение продукта по id
        return productService.getById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Product product) {   // добавление продукта
        productService.save(product);
    }

    @PutMapping(value = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @RequestBody Product product) {  // обновление продукта
        productService.update(id, product);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @DeleteMapping(value = "/delete/list/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByProductId(@PathVariable String productId) {
        productService.deleteByProductId(productId);
    }

    @GetMapping(value = "/status")   // http://localhost:8080/products/status?status=Sellable
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductByStatus(@RequestParam String status) {   // 2.1. Реализовать фильтрацию продуктов по статусу (Sellable, Unfulfillable, Inbound).
        return productService.getProductByStatus(status);
    }

    @GetMapping(value = "/value/status/sellable")
    @ResponseStatus(HttpStatus.OK)
    public int getTotalValueByStatusSellable() {  // 2.2. Реализовать возможность запроса общего значения (value) всех продуктов с состоянием Sellable
        return productService.getTotalValueByStatusSellable();
    }

    @GetMapping(value = "/value/status")   // http://localhost:8080/products/value/status?status=Sellable
    @ResponseStatus(HttpStatus.OK)
    public int getTotalValueByStatus(@RequestParam String status) {  // Реализовать возможность запроса общего значения (value) всех продуктов для конкретного статуса
        return productService.getTotalValueByStatus(status);
    }

    @GetMapping(value = "/value/fulfillment_center")   // http://localhost:8080/products/value/fulfillment_center?ffmCenter=fc5
    @ResponseStatus(HttpStatus.OK)
    public int getTotalValueByFulfillmentCenter(@RequestParam String ffmCenter) {  // 5.1. Создать метод для вычисления общего значения (value) всех продуктов для конкретного Fulfillment Center
        return productService.getTotalValueByFulfillmentCenter(ffmCenter);
    }

    @PutMapping(value = "/decrease/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void decreaseQuantityByProductId(@PathVariable String productId) {
        productService.decreaseQuantityByProductId(productId);
    }

    @PutMapping(value = "/decrease/one/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void decreaseQuantityById(@PathVariable Long id) {
        productService.decreaseQuantityById(id);
    }
}