package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
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

import java.util.List;
import java.util.Optional;
//@Api(value = "CRUD REST APIs for Customer Resource")
@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("")
    @Operation(summary = "Get all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved customers",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Customer.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved customer",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    public Optional<Customer> getCustomerById(@PathVariable("id") Integer id) {
        return customerRepository.findById(id);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    public ResponseEntity<String> deleteCustomerById(@PathVariable("id") Integer id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return ResponseEntity.ok("Customer with ID " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with ID " + id + " does not exist");
        }
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id, @RequestBody Customer updatedCustomer) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with ID " + id + " does not exist");
        }

        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer != null) {
            if (updatedCustomer.getFirstName() != null) {
                existingCustomer.setFirstName(updatedCustomer.getFirstName());
            }
            if (updatedCustomer.getLastName() != null) {
                existingCustomer.setLastName(updatedCustomer.getLastName());
            }
            if (updatedCustomer.getBirthDate() != null) {
                existingCustomer.setBirthDate(updatedCustomer.getBirthDate());
            }

            customerRepository.save(existingCustomer);
            return ResponseEntity.ok(existingCustomer);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update customer");
    }

    @PostMapping("")
    @Operation(summary = "Add a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer added successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content)
    })
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        if (customer == null) {
            return ResponseEntity.badRequest().body("Invalid request body");
        }
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer added successfully");
    }
}