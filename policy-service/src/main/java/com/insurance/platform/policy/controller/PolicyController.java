package com.insurance.platform.policy.controller;

import com.insurance.platform.policy.domain.Policy;
import com.insurance.platform.policy.service.PolicyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    private final PolicyService service;

    public PolicyController(PolicyService service) {
        this.service = service;
    }

    @PostMapping
    public Policy createPolicy(@RequestBody Policy policy) {
        return service.createPolicy(policy);
    }

    @GetMapping("/{id}")
    public Policy getPolicy(@PathVariable Long id) {
        return service.getPolicy(id);
    }
}
