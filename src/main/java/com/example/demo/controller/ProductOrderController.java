package com.example.demo.controller;

import com.example.demo.ProductOrderPK;
import com.example.demo.entity.ProductOrder;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductOrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductOrderController {
    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/product-orders")
    public List<ProductOrder> getAllProductOrders() {
        return productOrderRepository.findAll();
    }

    @GetMapping("/product-orders/{productId}/{orderId}")
    public Optional<ProductOrder> getProductOrder(@PathVariable("productId") int productId, @PathVariable("orderId") int orderId) {
        return productOrderRepository.findById(new ProductOrderPK(productId, orderId));
    }

    @DeleteMapping("/product-orders/{productId}/{orderId}")
    public ResponseEntity<String> deleteProductOrder(@PathVariable("productId") int productId, @PathVariable("orderId") int orderId) {
        if (productOrderRepository.existsById(new ProductOrderPK(productId, orderId))) {
            productOrderRepository.deleteById(new ProductOrderPK(productId, orderId));
            return ResponseEntity.ok("Product Order with Product ID " + productId + " and Order ID " + orderId + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Order with Product ID " + productId + " and Order ID " + orderId + " does not exist");
        }
    }

    @PutMapping("/product-orders/{productId}/{orderId}")
    public ResponseEntity<?> updateProductOrder(
            @PathVariable("productId") int productId,
            @PathVariable("orderId") int orderId,
            @RequestBody ProductOrder productOrder
    ) {
        Optional<ProductOrder> existingProductOrder = productOrderRepository.findById(new ProductOrderPK(productId, orderId));
        if (existingProductOrder.isPresent()) {
            ProductOrder existingOrder = existingProductOrder.get();
            existingOrder.setQuantity(productOrder.getQuantity());
            existingOrder.setPrice(productOrder.getPrice());
            existingOrder.setVar(productOrder.getVar());

            productOrderRepository.save(existingOrder);
            return ResponseEntity.ok(existingOrder);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Order with Product ID " + productId + " and Order ID " + orderId + " does not exist");
    }

    @PostMapping("/product-orders/{productId}/{orderId}")
    public ResponseEntity<String> addProductOrder(
            @PathVariable("productId") int productId,
            @PathVariable("orderId") int orderId,
            @RequestBody ProductOrder productOrder
    ) {
        if (!productRepository.existsById(productId)) {
            return ResponseEntity.badRequest().body("Product with ID " + productId + " does not exist");
        }

        if (!orderRepository.existsById(orderId)) {
            return ResponseEntity.badRequest().body("Order with ID " + orderId + " does not exist");
        }

        productOrder.setId(new ProductOrderPK(productId, orderId));
        productOrderRepository.save(productOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product Order added successfully");
    }
}
