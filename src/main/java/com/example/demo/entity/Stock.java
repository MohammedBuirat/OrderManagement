package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="stocks")
@NoArgsConstructor
@AllArgsConstructor

public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "product_id",nullable = false)
    private Integer productId;
    @Column(nullable = false)
    private Integer quantity;
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;
}
