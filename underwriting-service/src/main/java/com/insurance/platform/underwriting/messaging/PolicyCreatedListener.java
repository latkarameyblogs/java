package com.insurance.platform.underwriting.messaging;

import com.insurance.platform.underwriting.event.PolicyCreatedEvent;
import com.insurance.platform.underwriting.event.RiskEvaluatedEvent;
import com.insurance.platform.underwriting.messaging.command.EvaluateRiskCommand;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PolicyCreatedListener {

    private final UnderwritingEventProducer producer;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PolicyCreatedListener(UnderwritingEventProducer producer, KafkaTemplate<String, Object> kafkaTemplate) {
        this.producer = producer;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(
            topics = "policy-created",
            groupId = "underwriting-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.underwriting.event.PolicyCreatedEvent"
            }
    )
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


    @KafkaListener(
            topics = "evaluate-risk-command",
            groupId = "underwriting-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.underwriting.messaging.command.EvaluateRiskCommand"
            }
    )
    public void handleEvaluateRiskCommand(EvaluateRiskCommand command) {

        System.out.println("Received EvaluateRiskCommand for policyId: "
                + command.getPolicyId());

        // TEMP: simulate approval
        boolean approved = true;

        RiskEvaluatedEvent event =
                new RiskEvaluatedEvent(
                        command.getPolicyId(),
                        approved,
                        approved ? "Approved" : "Rejected"
                );

        kafkaTemplate.send("risk-evaluated", event);

        System.out.println("RiskEvaluatedEvent published for policyId: "
                + command.getPolicyId());
    }

}