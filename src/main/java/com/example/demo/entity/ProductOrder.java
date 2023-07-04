package com.example.demo.entity;

import com.example.demo.ProductOrderPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="product_order")
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder implements Serializable {
    //using an embedded id since we have two foreign key without a primary key
    @EmbeddedId
    private ProductOrderPK id;
    @Column(nullable = false)
    private int quantity;
    @Column(precision = 10, scale = 2,nullable = false)
    private BigDecimal price;
    @Column(precision = 10, scale = 2,nullable = false)
    private BigDecimal var;
}
