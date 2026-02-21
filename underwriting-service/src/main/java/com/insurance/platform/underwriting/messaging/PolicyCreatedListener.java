package com.insurance.platform.underwriting.messaging;

import com.insurance.platform.underwriting.event.PolicyCreatedEvent;
import com.insurance.platform.underwriting.event.RiskEvaluatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PolicyCreatedListener {

    private final UnderwritingEventProducer producer;

    public PolicyCreatedListener(UnderwritingEventProducer producer) {
        this.producer = producer;
    }

    @KafkaListener(topics = "policy-created", groupId = "underwriting-group")
    public void handlePolicyCreated(PolicyCreatedEvent event) {

        boolean approved;
        String reason;

        // Business rule
        if (event.getPremiumAmount().intValue() > 100000) {
            approved = false;
            reason = "Premium too high";
        } else {
            approved = true;
            reason = "Approved";
        }

        RiskEvaluatedEvent response =
                new RiskEvaluatedEvent(event.getPolicyId(), approved, reason);

        producer.publishRiskEvaluated(response);
    }
}