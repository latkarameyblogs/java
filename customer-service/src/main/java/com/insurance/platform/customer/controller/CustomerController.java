package com.insurance.platform.customer.controller;

import com.insurance.platform.customer.domain.Customer;
import com.insurance.platform.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import com.insurance.platform.customer.dto.CreateCustomerRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody CreateCustomerRequest request) {

        Customer customer = new Customer();
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setMobileNumber(request.getMobileNumber());

        return service.createCustomer(customer);
    }


    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return service.getCustomer(id);
    }
}
