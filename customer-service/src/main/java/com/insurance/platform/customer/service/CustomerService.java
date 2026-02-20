package com.insurance.platform.customer.service;

import com.insurance.platform.customer.domain.Customer;
import com.insurance.platform.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import com.insurance.platform.customer.exception.CustomerAlreadyExistsException;
import com.insurance.platform.customer.exception.CustomerNotFoundException;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer createCustomer(Customer customer) {

        repository.findByEmail(customer.getEmail())
                .ifPresent(c -> {
                    throw new CustomerAlreadyExistsException("Customer already exists with this email");
                });

        return repository.save(customer);
    }

    public Customer getCustomer(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id));
    }

}
