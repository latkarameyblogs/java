package com.insurance.platform.payment.messaging.listener;

import com.insurance.platform.payment.event.PaymentCompletedEvent;
import com.insurance.platform.payment.event.RiskEvaluatedEvent;
import com.insurance.platform.payment.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PaymentEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(PaymentEventListener.class);

    private final PaymentService paymentService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentEventListener(PaymentService paymentService,
                                KafkaTemplate<String, Object> kafkaTemplate) {
        this.paymentService = paymentService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(
            topics = "risk-evaluated",
            groupId = "payment-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.payment.event.RiskEvaluatedEvent"
            }
    )
    public void handleRiskEvaluated(RiskEvaluatedEvent event) {

        log.info("Received RiskEvaluatedEvent for policy {}", event.getPolicyId());

        if (!event.isApproved()) {
            log.info("Policy {} rejected. Skipping payment.", event.getPolicyId());
            return;
        }


       Boolean paymentSuccess = paymentService.processPayment(event.getPolicyId(),event.getSagaID());

        PaymentCompletedEvent completedEvent =
                new PaymentCompletedEvent(event.getSagaID(),event.getPolicyId(), paymentSuccess);

        kafkaTemplate.send("payment-completed", completedEvent);

        log.info("Published PaymentCompletedEvent for policy {}", event.getPolicyId());
    }
}