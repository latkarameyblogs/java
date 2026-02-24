package com.insurance.platform.saga.messaging.listener;

import com.insurance.platform.saga.domain.SagaInstance;
import com.insurance.platform.saga.domain.SagaStep;
import com.insurance.platform.saga.messaging.command.ProcessPaymentCommand;
import com.insurance.platform.saga.messaging.event.RiskEvaluatedEvent;
import com.insurance.platform.saga.messaging.producer.SagaCommandProducer;
import com.insurance.platform.saga.repository.SagaRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import  com.insurance.platform.saga.messaging.event.PaymentCompletedEvent;
import com.insurance.platform.saga.messaging.command.ActivatePolicyCommand;

import java.util.Optional;

@Component
public class SagaEventListener {

    private final SagaRepository sagaRepository;

    private final SagaCommandProducer commandProducer;

    public SagaEventListener(SagaRepository sagaRepository,SagaCommandProducer commandProducer) {
        this.sagaRepository = sagaRepository;
        this.commandProducer  = commandProducer;
    }

    @KafkaListener(
            topics = "risk-evaluated",
            groupId = "saga-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.saga.messaging.event.RiskEvaluatedEvent"
            }
    )
    public void handleRiskEvaluated(RiskEvaluatedEvent event) {

        System.out.println("Orchestrator received RiskEvaluatedEvent for policyId: "
                + event.getPolicyId());

        Optional<SagaInstance> optionalSaga =
                sagaRepository.findByPolicyId(event.getPolicyId());

        if (optionalSaga.isEmpty()) {

            System.out.println("Saga not found for policyId: "
                    + event.getPolicyId());

            return;
        }

        SagaInstance saga = optionalSaga.get();

        if (event.isApproved()) {

            saga.setCurrentStep(SagaStep.WAITING_FOR_PAYMENT);

            ProcessPaymentCommand command =
                    new ProcessPaymentCommand(event.getPolicyId());

            commandProducer.sendProcessPaymentCommand(command);

        } else {

            saga.setCurrentStep(SagaStep.COMPLETED_REJECTED);
        }

        sagaRepository.save(saga);

        System.out.println("Saga updated for policyId: "
                + event.getPolicyId());
    }

    @KafkaListener(
            topics = "payment-completed",
            groupId = "saga-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.saga.messaging.event.PaymentCompletedEvent"
            }
    )
    public void handlePaymentCompleted(PaymentCompletedEvent event) {

        System.out.println(
                "Orchestrator received PaymentCompletedEvent for policyId: "
                        + event.getPolicyId()
        );

        SagaInstance saga =
                sagaRepository.findByPolicyId(event.getPolicyId())
                        .orElseThrow();


        // ✅ IDEMPOTENCY GUARD
        if (saga.getCurrentStep() == SagaStep.COMPLETED_SUCCESS ||
                saga.getCurrentStep() == SagaStep.COMPLETED_CANCELLED) {

            System.out.println(
                    "Duplicate PaymentCompletedEvent ignored for policyId: "
                            + event.getPolicyId()
            );

            return;
        }


        if (event.isSuccess()) {

            saga.setCurrentStep(SagaStep.COMPLETED_SUCCESS);

            ActivatePolicyCommand command =
                    new ActivatePolicyCommand(event.getPolicyId());

            commandProducer.sendActivatePolicyCommand(command);

        } else {

            saga.setCurrentStep(SagaStep.COMPLETED_CANCELLED);
        }


        sagaRepository.save(saga);

        System.out.println(
                "Saga completed for policyId: "
                        + event.getPolicyId()
        );
    }
}