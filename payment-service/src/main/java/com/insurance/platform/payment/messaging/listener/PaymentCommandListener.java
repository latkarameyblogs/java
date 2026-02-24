package com.insurance.platform.payment.messaging.listener;

import com.insurance.platform.payment.event.PaymentCompletedEvent;
import com.insurance.platform.payment.messaging.command.ProcessPaymentCommand;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentCommandListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentCommandListener(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(
            topics = "process-payment-command",
            groupId = "payment-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.payment.messaging.command.ProcessPaymentCommand"
            }
    )
    public void handleProcessPayment(ProcessPaymentCommand command) {

        System.out.println("Payment Service received ProcessPaymentCommand for policyId: "
                + command.getPolicyId());

        // simulate success
        PaymentCompletedEvent event =
                new PaymentCompletedEvent(command.getPolicyId(), true);

        kafkaTemplate.send("payment-completed", event);

        System.out.println("PaymentCompletedEvent published for policyId: "
                + command.getPolicyId());
    }
}