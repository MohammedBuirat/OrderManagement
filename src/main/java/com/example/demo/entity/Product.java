package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor

public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String slug;
    @Column(nullable = false)
    private String name;
    private String reference;
    @Column(precision = 10, scale = 2,nullable = false)
    private BigDecimal price;
    @Column(precision = 10, scale = 2,nullable = false)
    private BigDecimal vat;
    @Column(nullable = false)
    private boolean stockable;

}
