package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="orders")
@NoArgsConstructor
@AllArgsConstructor

public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="customer_id",nullable = false)
    private Integer customerId;
    @Column(name="ordered_at",nullable = false)
    private LocalDateTime orderedAt;

}
