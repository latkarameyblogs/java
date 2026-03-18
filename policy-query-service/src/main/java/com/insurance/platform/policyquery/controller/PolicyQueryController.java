package com.insurance.platform.policyquery.controller;

import com.insurance.platform.policyquery.entity.PolicyView;
import com.insurance.platform.policyquery.entity.PolicyViewRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
public class PolicyQueryController {

    private final PolicyViewRepository repository;

    public PolicyQueryController(PolicyViewRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public PolicyView getPolicy(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @GetMapping
    public List<PolicyView> getAllPolicies() {
        return repository.findAll();
    }
}