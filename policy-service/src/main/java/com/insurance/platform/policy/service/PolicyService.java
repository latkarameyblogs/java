package com.insurance.platform.policy.service;

import com.insurance.platform.policy.client.CustomerClient;
import com.insurance.platform.policy.client.UnderwritingClient;
import com.insurance.platform.policy.client.PaymentClient;
import com.insurance.platform.policy.client.dto.RiskEvaluationRequest;
import com.insurance.platform.policy.client.dto.RiskEvaluationResponse;
import com.insurance.platform.policy.client.dto.PaymentResponse;
import com.insurance.platform.policy.domain.Policy;
import com.insurance.platform.policy.domain.PolicyStatus;
import com.insurance.platform.policy.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import com.insurance.platform.policy.messaging.PolicyEventProducer;
import com.insurance.platform.policy.event.PolicyCreatedEvent;

import java.util.UUID;

@Service
public class PolicyService {

    private final PolicyRepository repository;
    private final CustomerClient customerClient;
//    private final UnderwritingClient underwritingClient;
//    private final PaymentClient paymentClient;
    private final PolicyEventProducer eventProducer;

    public PolicyService(
            PolicyRepository repository,
            CustomerClient customerClient,
//            UnderwritingClient underwritingClient,
//            PaymentClient paymentClient,
            PolicyEventProducer eventProducer) {
        this.repository = repository;
        this.customerClient = customerClient;
//        this.underwritingClient = underwritingClient;
//        this.paymentClient = paymentClient;
        this.eventProducer = eventProducer;
    }

    /**
     * Orchestrates distributed SAGA workflow for insurance policy issuance:
     * 1. Validate Customer
     * 2. Create Policy (UNDER_REVIEW)
     * 3. Call Underwriting Service
     * 4. Process Payment
     * 5. Activate policy or handle REJECTED/CANCELLED
     */
//    public Policy createPolicy(Policy policy) {
//
//        // Step 1: Validate Customer existence
//        if (!customerClient.customerExists(policy.getCustomerId())) {
//            throw new RuntimeException("Customer does not exist");
//        }
//
//        // Step 2: Initialize Policy
//        policy.setPolicyNumber("POL-" + UUID.randomUUID().toString().substring(0, 8));
//        policy.setStatus(PolicyStatus.UNDER_REVIEW);
//        repository.save(policy);
//        PolicyCreatedEvent event = new PolicyCreatedEvent(
//                policy.getId(),
//                policy.getCustomerId(),
//                policy.getPolicyType(),
//                policy.getPremiumAmount()
//        );
//
//        eventProducer.publishPolicyCreated(event);
//
//        // Step 3: Call Underwriting Service (distributed)
//        RiskEvaluationRequest riskRequest = new RiskEvaluationRequest(
//                policy.getPremiumAmount(),
//                policy.getPolicyType()
//        );
//        RiskEvaluationResponse riskResponse = underwritingClient.evaluateRisk(riskRequest);
//
//        if (!riskResponse.isApproved()) {
//            policy.setStatus(PolicyStatus.REJECTED);
//            return repository.save(policy);
//        }
//
//        // Step 4: Payment processing (distributed via PaymentClient)
//        policy.setStatus(PolicyStatus.PAYMENT_PENDING);
//        repository.save(policy);
//
//        PaymentResponse paymentResponse = paymentClient.processPayment(
//                policy.getCustomerId(),
//                policy.getPremiumAmount()
//        );
//
//        if (!paymentResponse.isSuccess()) {
//            policy.setStatus(PolicyStatus.CANCELLED);
//            return repository.save(policy);
//        }
//
//        // Step 5: Activate Policy
//        policy.setStatus(PolicyStatus.ACTIVE);
//        return repository.save(policy);
//    }

    //Choreography SAGA

    public Policy createPolicy(Policy policy) {

        // Validate customer (still synchronous is fine)
        if (!customerClient.customerExists(policy.getCustomerId())) {
            throw new RuntimeException("Customer does not exist");
        }

        policy.setPolicyNumber("POL-" + UUID.randomUUID().toString().substring(0, 8));
        policy.setStatus(PolicyStatus.UNDER_REVIEW);

        Policy savedPolicy = repository.save(policy);

        // Publish event
        PolicyCreatedEvent event = new PolicyCreatedEvent(
                savedPolicy.getId(),
                savedPolicy.getCustomerId(),
                savedPolicy.getPolicyType(),
                savedPolicy.getPremiumAmount()
        );

        eventProducer.publishPolicyCreated(event);

        return savedPolicy;
    }



    /**
     * Fetch a policy by ID
     */
    public Policy getPolicy(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found with id " + id));
    }
}