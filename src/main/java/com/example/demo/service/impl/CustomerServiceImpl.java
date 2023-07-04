package com.example.demo.service.impl;


import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {


    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    public CustomerServiceImpl(OrderRepository orderRepository,CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
        this.orderRepository=orderRepository;
    }


    @Override
    public Customer createCustomer(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> getCustomers() {
        return null;
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return null;
    }

    @Override
    public Customer updateCustomer(Integer id, Customer customer) {
        return null;
    }

    @Override
    public void deleteCustomer(Integer id) {

    }
}
