package com.insurance.platform.underwriting.messaging;

import com.insurance.platform.underwriting.event.RiskEvaluatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UnderwritingEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UnderwritingEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishRiskEvaluated(RiskEvaluatedEvent event) {
        kafkaTemplate.send("risk-evaluated", event);
    }
}