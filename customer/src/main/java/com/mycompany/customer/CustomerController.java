package com.mycompany.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        int a = 10;
    }

    @PostMapping
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerRequest customerRequest) {
            customerService.registerCustomer(customerRequest);
            return ResponseEntity.ok("Customer registered successfully");

    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }
}
