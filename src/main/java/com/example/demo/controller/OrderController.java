package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductOrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @GetMapping("")
    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved orders",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Order.class)))}),
            @ApiResponse(responseCode = "404", description = "No orders found",
                    content = @Content)
    })
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))}),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)
    })
    public Optional<Order> getOrderById(@PathVariable("id") Integer id) {
        return orderRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)
    })
    public ResponseEntity<String> deleteOrderById(@PathVariable("id") Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return ResponseEntity.ok("Order with ID " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + id + " does not exist");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)
    })
    public ResponseEntity<?> updateOrderById(@PathVariable("id") Integer id, @RequestBody Order updatedOrder) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + id + " does not exist");
        }

        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            if (updatedOrder.getCustomerId() != null) {
                if (customerRepository.existsById(updatedOrder.getCustomerId())) {
                    existingOrder.setCustomerId(updatedOrder.getCustomerId());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with ID " + updatedOrder.getCustomerId() + " does not exist");
                }
            }
            existingOrder.setOrderedAt(LocalDateTime.now());
            orderRepository.save(existingOrder);
            return ResponseEntity.ok(existingOrder);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update order");
    }

    @PostMapping("")
    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the order",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content)
    })
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        if (order == null || order.getCustomerId() == null) {
            return ResponseEntity.badRequest().body("Invalid request body");
        }

        if (!customerRepository.existsById(order.getCustomerId())) {
            return ResponseEntity.badRequest().body("Customer ID does not exist");
        }

        order.setOrderedAt(LocalDateTime.now());
        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order added successfully");
    }
}
