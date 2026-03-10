package com.insurance.platform.policy.controller;

import com.insurance.platform.policy.service.PolicyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    private final PolicyService service;

    public PolicyController(PolicyService service) {
        this.service = service;
    }

    //Commenting this as this will be called by the Saga Orchestrator and we are not exposing any API for creating policy directly. We can add this back if we want to expose API for creating policy directly without going through the saga orchestrator.

//    @PostMapping
//    public Policy createPolicy(@RequestBody Policy policy) {
//        return service.createPolicy(policy);
//    }
//
//    @GetMapping("/{id}")
//    public Policy getPolicy(@PathVariable Long id) {
//        return service.getPolicy(id);
//    }
}
