package com.insurance.platform.policy.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.platform.events.PolicyCreatedEvent;
import com.insurance.platform.policy.messaging.PolicyEventProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxRelayPublisher {

    private final OutboxEventRepository repository;
    private final PolicyEventProducer producer;
    private final ObjectMapper objectMapper;

    public OutboxRelayPublisher(OutboxEventRepository repository,
                                PolicyEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
        this.objectMapper = new ObjectMapper();
    }

    @Scheduled(fixedRate = 5000)
    public void publishEvents() throws Exception {

        List<OutboxEvent> events = repository.findByPublishedFalse();

        for (OutboxEvent event : events) {

            if ("POLICY_CREATED".equals(event.getEventType())) {

                PolicyCreatedEvent policyEvent =
                        objectMapper.readValue(event.getPayload(), PolicyCreatedEvent.class);

                producer.publishPolicyCreated(policyEvent);

                event.setPublished(true);
                repository.save(event);
            }
        }
    }
}