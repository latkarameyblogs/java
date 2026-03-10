package com.insurance.platform.saga.messaging.producer;

import com.insurance.platform.saga.messaging.command.ActivatePolicyCommand;
import com.insurance.platform.saga.messaging.command.CreatePolicyCommand;
import com.insurance.platform.saga.messaging.command.EvaluateRiskCommand;
import com.insurance.platform.saga.messaging.command.ProcessPaymentCommand;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SagaCommandProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SagaCommandProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendCreatePolicyCommand(CreatePolicyCommand command) {
        kafkaTemplate.send("create-policy-command", command);

        System.out.println(
                "sendCreatePolicyCommand to create policy: "
                        + command.getCustomerId()
        );
    }


    /**
     * Step 1: Ask Underwriting to evaluate risk
     */
    public void sendEvaluateRiskCommand(EvaluateRiskCommand command) {

        kafkaTemplate.send("evaluate-risk-command", command);

        System.out.println(
                "EvaluateRiskCommand sent for policyId: "
                        + command.getPolicyId()
        );
    }


    /**
     * Step 2: Ask Payment service to process payment
     */
    public void sendProcessPaymentCommand(ProcessPaymentCommand command) {

        kafkaTemplate.send("process-payment-command", command);

        System.out.println(
                "ProcessPaymentCommand sent for policyId: "
                        + command.getPolicyId()
        );
    }


    /**
     * Step 3: Ask Policy service to activate policy
     */
    public void sendActivatePolicyCommand(ActivatePolicyCommand command) {

        kafkaTemplate.send("activate-policy-command", command);

        System.out.println(
                "ActivatePolicyCommand sent for policyId: "
                        + command.getPolicyId()
        );
    }

}