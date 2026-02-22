//package com.insurance.platform.policy.messaging;
//
//import com.insurance.platform.policy.domain.Policy;
//import com.insurance.platform.policy.domain.PolicyStatus;
//import com.insurance.platform.policy.event.RiskEvaluatedEvent;
//import com.insurance.platform.policy.repository.PolicyRepository;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RiskEvaluatedListener {
//
//    private final PolicyRepository repository;
//
//    public RiskEvaluatedListener(PolicyRepository repository) {
//        this.repository = repository;
//    }
//
//    @KafkaListener(topics = "risk-evaluated", groupId = "policy-group")
//    public void handleRiskEvaluated(RiskEvaluatedEvent event) {
//
//        Policy policy = repository.findById(event.getPolicyId())
//                .orElseThrow();
//
//        if (!event.isApproved()) {
//            policy.setStatus(PolicyStatus.REJECTED);
//        } else {
//            policy.setStatus(PolicyStatus.PAYMENT_PENDING);
//        }
//
//        repository.save(policy);
//    }
//}