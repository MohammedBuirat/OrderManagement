package com.example.demo.service;

import com.example.demo.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    List<Customer> getCustomers();
    Customer getCustomerById(Integer id);
    Customer updateCustomer(Integer id,Customer customer);
    void deleteCustomer(Integer id);


}
