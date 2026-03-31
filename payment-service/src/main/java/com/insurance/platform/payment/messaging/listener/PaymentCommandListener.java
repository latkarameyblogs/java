package com.insurance.platform.payment.messaging.listener;

import com.insurance.platform.payment.messaging.command.ProcessPaymentCommand;
import com.insurance.platform.payment.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCommandListener {

    private final PaymentService paymentService;

    public PaymentCommandListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(
            topics = "process-payment-command",
            groupId = "payment-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.payment.messaging.command.ProcessPaymentCommand"
            }
    )
    public void handleProcessPayment(ProcessPaymentCommand command) {

        System.out.println(
                "Payment Service received ProcessPaymentCommand for policyId: "
                        + command.getPolicyId()
        );

        paymentService.processPayment(
                command.getPolicyId(),
                command.getSagaId(),
                command.getUserId()
        );
    }
}