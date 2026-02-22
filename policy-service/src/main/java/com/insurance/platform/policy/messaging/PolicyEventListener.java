package com.insurance.platform.policy.messaging;

import com.insurance.platform.policy.domain.Policy;
import com.insurance.platform.policy.domain.PolicyStatus;
import com.insurance.platform.policy.event.PaymentCompletedEvent;
import com.insurance.platform.policy.event.RiskEvaluatedEvent;
import com.insurance.platform.policy.repository.PolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PolicyEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(PolicyEventListener.class);

    private final PolicyRepository repository;

    public PolicyEventListener(PolicyRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = "risk-evaluated",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.policy.event.RiskEvaluatedEvent"
            }
    )
    public void handleRiskEvaluated(RiskEvaluatedEvent event) {

        log.info("Received RiskEvaluatedEvent for policy {}", event.getPolicyId());

        Policy policy = repository.findById(event.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        if (!event.isApproved()) {
            policy.setStatus(PolicyStatus.REJECTED);
        } else {
            policy.setStatus(PolicyStatus.PAYMENT_PENDING);
        }

        repository.save(policy);
    }

    @KafkaListener(
            topics = "payment-completed",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.policy.event.PaymentCompletedEvent"
            }
    )
    public void handlePaymentCompleted(PaymentCompletedEvent event) {

        log.info("Received PaymentCompletedEvent for policy {}", event.getPolicyId());

        Policy policy = repository.findById(event.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        if (policy.getStatus() == PolicyStatus.ACTIVE) {
            log.info("Policy already ACTIVE. Ignoring duplicate event.");
            return;
        }

        if (event.isSuccess()) {

            policy.setStatus(PolicyStatus.ACTIVE);
        } else {
            policy.setStatus(PolicyStatus.CANCELLED);
        }

        repository.save(policy);
    }
}