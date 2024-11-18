package com.example.product_project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.product_project.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.status = :status")
    List<Product> getProductByStatus(@Param("status") String status);

    @Query("SELECT SUM(p.value) FROM Product p WHERE p.status = 'Sellable'")
    int getTotalValueByStatusSellable();

    @Query("SELECT SUM(p.value) FROM Product p WHERE p.status = :status")
    int getTotalValueByStatus(@Param("status") String status);

    @Query("SELECT SUM(p.value) FROM Product p WHERE p.fulfillment_center = :fulfillmentCenter")
    int getTotalValueByFulfillmentCenter(@Param("fulfillmentCenter") String fulfillmentCenter);

    @Query(value = "SELECT * FROM product p WHERE p.product_id = :productId", nativeQuery = true)
    List<Product> findByProductId(String productId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Product p WHERE p.product_id = :productId")
    void deleteByProductId(String productId);
}
