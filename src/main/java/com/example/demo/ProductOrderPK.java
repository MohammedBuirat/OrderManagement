package com.example.demo;

import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class ProductOrderPK  implements Serializable {
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "order_id")
    private Integer orderId;
}
