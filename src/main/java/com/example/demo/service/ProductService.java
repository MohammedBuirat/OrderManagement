package com.example.demo.service;

import com.example.demo.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getProducts();
    Product getProductById(Integer id);
    Product updateProduct(Integer id, Product product);
    void deleteProduct(Integer id);
}