package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Product.class)))}),
            @ApiResponse(responseCode = "404", description = "No products found",
                    content = @Content)
    })
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    public Optional<Product> getProductById(@PathVariable("id") Integer id) {
        return productRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    public ResponseEntity<String> deleteProductById(@PathVariable("id") Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product with ID " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " does not exist");
        }
    }

    @PostMapping("")
    @Operation(summary = "Add a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the product",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content)
    })
    public ResponseEntity<String> addProduct(@RequestBody Map<String, String> body) {
        if (body.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid request body");
        }

        Product product = new Product();
        if (body.containsKey("slug")) {
            product.setSlug(body.get("slug"));
        }
        if (body.containsKey("name")) {
            product.setName(body.get("name"));
        }
        if (body.containsKey("reference")) {
            product.setReference(body.get("reference"));
        }
        if (body.containsKey("price")) {
            product.setPrice(new BigDecimal(body.get("price")));
        }
        if (body.containsKey("vat")) {
            product.setVat(new BigDecimal(body.get("vat")));
        }
        if (body.containsKey("stockable")) {
            product.setStockable(Boolean.parseBoolean(body.get("stockable")));
        }

        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    public ResponseEntity<?> updateProductById(@PathVariable("id") Integer id, @RequestBody Map<String, Object> body) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " does not exist");
        }

        Product current = productRepository.findById(id).orElse(null);

        if (current != null) {
            if (body.containsKey("slug")) {
                current.setSlug((String) body.get("slug"));
            }
            if (body.containsKey("name")) {
                current.setName((String) body.get("name"));
            }
            if (body.containsKey("reference")) {
                current.setReference((String) body.get("reference"));
            }
            if (body.containsKey("price")) {
                current.setPrice(new BigDecimal(String.valueOf(body.get("price"))));
            }
            if (body.containsKey("vat")) {
                current.setVat(new BigDecimal(String.valueOf(body.get("vat"))));
            }
            if (body.containsKey("stockable")) {
                current.setStockable(Boolean.parseBoolean(String.valueOf(body.get("stockable"))));
            }

            productRepository.save(current);
            return ResponseEntity.ok(current);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update the product");
        }
    }

    private ProductDto productToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setSlug(product.getSlug());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}