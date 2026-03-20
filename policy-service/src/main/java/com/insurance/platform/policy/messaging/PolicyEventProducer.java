package com.insurance.platform.policy.messaging;

import com.insurance.platform.events.PolicyCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PolicyEventProducer {

    private static final String TOPIC = "policy-created";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PolicyEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPolicyCreated(PolicyCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}