package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Stock;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StockRepository;
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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@Api(value = "CRUD REST APIs for Stock Resource")
@RestController
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    @Operation(summary = "Get all stocks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stocks",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Stock.class)))}),
            @ApiResponse(responseCode = "404", description = "No stocks found",
                    content = @Content)
    })
    public List<Stock> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        if (stocks.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No stocks found");
        }
        return stocks;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get stock by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the stock",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Stock.class))}),
            @ApiResponse(responseCode = "404", description = "Stock not found",
                    content = @Content)
    })
    public Optional<Stock> getStockById(@PathVariable("id") Integer id) {
        return stockRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete stock by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Stock not found",
                    content = @Content)
    })
    public ResponseEntity<String> deleteStockById(@PathVariable("id") Integer id) {
        if (stockRepository.existsById(id)) {
            stockRepository.deleteById(id);
            return ResponseEntity.ok("Stock with ID " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock with ID " + id + " does not exist");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update stock by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the stock",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Stock.class))}),
            @ApiResponse(responseCode = "404", description = "Stock or product not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Failed to update stock",
                    content = @Content)
    })
    public ResponseEntity<?> updateStockById(@PathVariable("id") Integer id, @RequestBody Stock updatedStock) {
        if (!stockRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock with ID " + id + " does not exist");
        }

        Stock existingStock = stockRepository.findById(id).orElse(null);
        if (existingStock != null) {
            if (updatedStock.getProductId() != null) {
                if (productRepository.existsById(updatedStock.getProductId())) {
                    existingStock.setProductId(updatedStock.getProductId());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + updatedStock.getProductId() + " does not exist");
                }
            }
            if (updatedStock.getQuantity() != null) {
                existingStock.setQuantity(updatedStock.getQuantity());
            }
            existingStock.setUpdatedAt(LocalDateTime.now());

            stockRepository.save(existingStock);
            return ResponseEntity.ok(existingStock);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update stock");
    }


    @Operation(summary = "Add a new stock")
    @PostMapping("")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the customer",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content)
    })
    public ResponseEntity<String> addStock(@RequestBody Stock stock) {
        if (stock == null || stock.getProductId() == null) {
            return ResponseEntity.badRequest().body("Invalid request body");
        }

        if (!productRepository.existsById(stock.getProductId())) {
            return ResponseEntity.badRequest().body("Product ID does not exist");
        }

        stock.setUpdatedAt(LocalDateTime.now());
        stockRepository.save(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body("Stock added successfully");
    }
}