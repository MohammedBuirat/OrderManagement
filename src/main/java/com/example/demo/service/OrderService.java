package com.example.demo.service;

import com.example.demo.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getOrders();
    Order getOrderById(Integer id);
    Order updateOrder(Integer id, Order order);
    void deleteOrder(Integer id);
}