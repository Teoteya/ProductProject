package com.example.product_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.product_project.entity.Product;
import org.springframework.transaction.annotation.Transactional;
import com.example.product_project.repository.ProductRepository;
import com.example.product_project.exception.ProductNotFoundException;
import com.example.product_project.exception.NoQuantityAvailableException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAll() {     // получение списка продуктов
        return productRepository.findAll();
    }

    public Product getById(Long id) {   // получение продукта по id
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format("product with id %s doesn't not exist", id)));
    }

    public List<Product> getByProductId(String productId) {   // получение списка продуктов по productId
        return productRepository.findByProductId(productId);
    }

    public void save(Product product) {
        Objects.requireNonNull(product.getProduct_id(), "Product ID cannot be null");  // product.getProductId() != null
        Objects.requireNonNull(product.getStatus(), "Status cannot be null");   // product.getStatus() != null
        Objects.requireNonNull(product.getFulfillment_center(), "Fulfillment Center cannot be null");  // product.getFulfillmentCenter() != null

        if (product.getQuantity() >= 0 && product.getValue() >= 0) {
            productRepository.save(product);
        }
        else
            throw new IllegalArgumentException("values must not be zero or null");
    }

    public void update(Long id, Product product) {
        Product existingProduct = getById(id);
        if (product.getQuantity() >= 0) {
            existingProduct.setQuantity(product.getQuantity());
        } else {
            throw new IllegalArgumentException("Quantity must be non-negative");
        }

        if (product.getValue() >= 0) {
            existingProduct.setValue(product.getValue());
        } else {
            throw new IllegalArgumentException("Value must be positive");
        }

        if (product.getStatus() != null) {
            existingProduct.setStatus(product.getStatus());
        }
        if (product.getFulfillment_center() != null) {
            existingProduct.setFulfillment_center(product.getFulfillment_center());
        }
        if (product.getProduct_id() != null) {
            existingProduct.setProduct_id(product.getProduct_id());
        }
        productRepository.save(existingProduct);
    }

    public void delete(Long id) {            // удаление самого продукта с N-количеством запасов
        productRepository.deleteById(id);
    }

    public void deleteByProductId(String productId) {
        List<Product> products = productRepository.findByProductId(productId);
        if (!products.isEmpty()) {
            productRepository.deleteByProductId(productId);
        } else {
            throw new ProductNotFoundException(String.format("product with id %s doesn't not exist", productId));
        }
    }

    public List<Product> getProductByStatus(String status) {
        return productRepository.getProductByStatus(status);
    }

    public int getTotalValueByStatusSellable() {
        return productRepository.getTotalValueByStatusSellable();
    }

    public int getTotalValueByStatus(String status) {
        return productRepository.getTotalValueByStatus(status);
    }

    public int getTotalValueByFulfillmentCenter(String fulfillmentCenter) {
        return productRepository.getTotalValueByFulfillmentCenter(fulfillmentCenter);
    }

    public void decreaseQuantityByProductId(String productId) {
        List<Product> list = getByProductId(productId);
        if (list != null) {
            List<Product> resultList = list.stream()
                    .filter(p -> p.getQuantity() > 1)
                    .peek(product -> product.setQuantity(product.getQuantity() - 1))
                    .toList();
            productRepository.saveAll(resultList);
        }
    }

    public void decreaseQuantityById(Long id) {    // не удаление самого продукта, а уменьшение его количества на 1 шт
        Product product = getById(id);
        if (product.getQuantity() > 0) {
            product.setQuantity(product.getQuantity() - 1);
            productRepository.save(product);
        } else {
            throw new NoQuantityAvailableException(String.format("the quantity of product with id %s is equal to zero", id));   // no available quantities of products with id %s
        }
    }
}