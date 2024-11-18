package com.example.product_project.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String product_id;

    @Column(nullable = false)
    String status;             // "SELLABLE", "UNFULFILLABLE", "INBOUND";

    @Column(nullable = false, name = "fulfillment_center")
    String fulfillment_center;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false)
    int value;
}