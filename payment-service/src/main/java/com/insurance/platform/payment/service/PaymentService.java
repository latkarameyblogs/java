package com.insurance.platform.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.platform.payment.outbox.OutboxEvent;
import com.insurance.platform.payment.outbox.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.insurance.platform.events.PaymentCompletedEvent;

import java.util.UUID;

@Service
public class PaymentService {

    private final OutboxEventRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public PaymentService(OutboxEventRepository outboxRepository,
                          ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Boolean processPayment(Long policyId, String sagaId,String userId) {

        System.out.println(
                "Processing payment for policyId: " + policyId
        );

        // Simulated payment logic
        boolean success = true;

        PaymentCompletedEvent event =new PaymentCompletedEvent( sagaId,
                policyId,
                success,userId);
        event.setEventId(UUID.randomUUID().toString());

        try {

            String payload =
                    objectMapper.writeValueAsString(event);

            OutboxEvent outboxEvent =
                    new OutboxEvent(
                            "PAYMENT",
                            policyId.toString(),
                            "PAYMENT_COMPLETED",
                            payload
                    );


            outboxRepository.save(outboxEvent);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}