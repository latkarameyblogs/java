package com.insurance.platform.payment.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.platform.payment.event.PaymentCompletedEvent;
import com.insurance.platform.payment.messaging.PaymentEventProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OutboxRelayPublisher {

    private final OutboxEventRepository repository;
    private final PaymentEventProducer producer;
    private final ObjectMapper objectMapper;

    public OutboxRelayPublisher(OutboxEventRepository repository,
                                PaymentEventProducer producer,
                                ObjectMapper objectMapper) {
        this.repository = repository;
        this.producer = producer;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void publishEvents() throws Exception {

        List<OutboxEvent> events =
                repository.findByPublishedFalse();

        for (OutboxEvent event : events) {

            if ("PAYMENT_COMPLETED".equals(event.getEventType())) {

                PaymentCompletedEvent paymentCompletedEvent =
                        objectMapper.readValue(
                                event.getPayload(),
                                PaymentCompletedEvent.class
                        );

                System.out.println(
                        "Publishing PaymentCompletedEvent for policyId: "
                                + paymentCompletedEvent.getPolicyId()
                );

                producer.publishPaymentCompleted(paymentCompletedEvent);

                event.setPublished(true);

                repository.save(event);
            }
        }
    }
}