package com.insurance.platform.policy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.platform.events.PolicyCreatedEvent;
import com.insurance.platform.policy.client.CustomerClient;
import com.insurance.platform.policy.domain.Policy;
import com.insurance.platform.policy.domain.PolicyStatus;
import com.insurance.platform.policy.messaging.PolicyEventProducer;
import com.insurance.platform.policy.outbox.OutboxEvent;
import com.insurance.platform.policy.outbox.OutboxEventRepository;
import com.insurance.platform.policy.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Service
public class PolicyService {

    private final PolicyRepository repository;
    private final CustomerClient customerClient;
    //    private final UnderwritingClient underwritingClient;
//    private final PaymentClient paymentClient;
    private final PolicyEventProducer eventProducer;

    private final OutboxEventRepository outboxRepository;


    private static final Logger log = LoggerFactory.getLogger(PolicyService.class);

    public PolicyService(
            PolicyRepository repository,
            CustomerClient customerClient,
            PolicyEventProducer eventProducer,
            OutboxEventRepository outboxRepository) {
        this.repository = repository;
        this.customerClient = customerClient;
        this.eventProducer = eventProducer;
        this.outboxRepository = outboxRepository;
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
    @Transactional
    public Policy createPolicy(Policy policy, String sagaId,String userId) {

        if (!customerClient.customerExists(policy.getCustomerId())) {
            throw new RuntimeException("Customer does not exist");
        }

        policy.setPolicyNumber("POL-" + UUID.randomUUID().toString().substring(0, 8));
        policy.setStatus(PolicyStatus.UNDER_REVIEW);

        Policy savedPolicy = repository.save(policy);

        PolicyCreatedEvent event = new PolicyCreatedEvent(
                sagaId,
                savedPolicy.getId(),
                savedPolicy.getCustomerId(),
                savedPolicy.getPolicyType(),
                savedPolicy.getPremiumAmount()
        );
        event.setEventId(UUID.randomUUID().toString());
        event.setUserId(userId);
        log.info("UserId in event: {}", event.getUserId());

        ObjectMapper mapper = new ObjectMapper();
        String payload;

        try {
            payload = mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        OutboxEvent outboxEvent = new OutboxEvent(
                "POLICY",
                savedPolicy.getId().toString(),
                "POLICY_CREATED",
                payload
        );

        outboxRepository.save(outboxEvent);


        return savedPolicy;
    }


    @Transactional
    public void activatePolicy(Long policyId) {

        Policy policy = repository
                .findById(policyId)
                .orElseThrow();

        policy.setStatus(PolicyStatus.ACTIVE);

        repository.save(policy);

        System.out.println("Policy ACTIVATED for policyId: " + policyId);
    }


    /**
     * Fetch a policy by ID
     */
    public Policy getPolicy(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found with id " + id));
    }
}