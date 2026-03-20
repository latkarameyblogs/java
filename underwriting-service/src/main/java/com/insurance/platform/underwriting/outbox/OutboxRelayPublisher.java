package com.insurance.platform.underwriting.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.platform.events.RiskEvaluatedEvent;
import com.insurance.platform.underwriting.messaging.UnderwritingEventProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxRelayPublisher {

    private final OutboxEventRepository repository;
    private final UnderwritingEventProducer producer;
    private final ObjectMapper objectMapper;

    public OutboxRelayPublisher(OutboxEventRepository repository,
                                UnderwritingEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
        this.objectMapper = new ObjectMapper();
    }

    @Scheduled(fixedRate = 5000)
    public void publishEvents() throws Exception {

        List<OutboxEvent> events = repository.findByPublishedFalse();

        for (OutboxEvent event : events) {

            if ("RISK_EVALUATED".equals(event.getEventType())) {

                RiskEvaluatedEvent riskEvaluatedEvent =
                        objectMapper.readValue(event.getPayload(), RiskEvaluatedEvent.class);

                producer.publishRiskEvaluated(riskEvaluatedEvent);

                event.setPublished(true);
                repository.save(event);
            }
        }
    }
}