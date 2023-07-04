package com.example.demo.service;

import com.example.demo.ProductOrderPK;
import com.example.demo.entity.ProductOrder;

import java.util.List;

public interface ProductOrderService {
    ProductOrder createProductOrder(ProductOrder productOrder);
    List<ProductOrder> getProductOrders();
    ProductOrder getProductOrderById(ProductOrderPK id);
    ProductOrder updateProductOrder(ProductOrderPK id, ProductOrder productOrder);
    void deleteProductOrder(ProductOrderPK id);
}