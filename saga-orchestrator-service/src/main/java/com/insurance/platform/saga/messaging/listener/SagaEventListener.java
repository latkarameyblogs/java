package com.insurance.platform.saga.messaging.listener;

import com.insurance.platform.events.PaymentCompletedEvent;
import com.insurance.platform.events.PolicyCreatedEvent;
import com.insurance.platform.events.RiskEvaluatedEvent;
import com.insurance.platform.saga.domain.SagaInstance;
import com.insurance.platform.saga.domain.SagaStep;
import com.insurance.platform.saga.messaging.command.ActivatePolicyCommand;
import com.insurance.platform.saga.messaging.command.EvaluateRiskCommand;
import com.insurance.platform.saga.messaging.command.ProcessPaymentCommand;
import com.insurance.platform.saga.messaging.event.ProcessedEvent;
import com.insurance.platform.saga.messaging.producer.SagaCommandProducer;
import com.insurance.platform.saga.repository.ProcessedEventRepository;
import com.insurance.platform.saga.repository.SagaRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SagaEventListener {

    private final SagaRepository sagaRepository;

    private final ProcessedEventRepository processedEventRepository;

    private final SagaCommandProducer commandProducer;

    public SagaEventListener(SagaRepository sagaRepository, SagaCommandProducer commandProducer, ProcessedEventRepository processedEventRepository) {
        this.sagaRepository = sagaRepository;
        this.commandProducer = commandProducer;
        this.processedEventRepository = processedEventRepository;
    }

    @KafkaListener(
            topics = "policy-created",
            groupId = "saga-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.events.PolicyCreatedEvent"
            }
    )
    public void handlePolicyCreated(PolicyCreatedEvent event) {

        System.out.println(
                "Orchestrator received PolicyCreatedEvent for policyId: "
                        + event.toString()
        );

        if (processedEventRepository.existsById(event.getEventId())) {
            System.out.println("Duplicate PolicyCreatedEvent ignored: " + event.getEventId());
            return;
        }


        Optional<SagaInstance> optionalSaga =
                sagaRepository.findById(UUID.fromString(event.getSagaId()));


        if (optionalSaga.isEmpty()) {

            System.out.println(
                    "Saga not found for policyId: "
                            + event.getPolicyId()
            );

            return;
        }

        SagaInstance saga = optionalSaga.get();

        saga.setPolicyId(event.getPolicyId());
        saga.setCurrentStep(SagaStep.WAITING_FOR_RISK);

        EvaluateRiskCommand command =
                new EvaluateRiskCommand(event.getSagaId(), event.getPolicyId(), event.getPolicyType(), event.getPremiumAmount());

        commandProducer.sendEvaluateRiskCommand(command);

        sagaRepository.save(saga);
        processedEventRepository.save(new ProcessedEvent(event.getEventId()));

        System.out.println(
                "Saga progressed to risk evaluation for policyId: "
                        + event.getPolicyId()
        );
    }


    @KafkaListener(
            topics = "risk-evaluated",
            groupId = "saga-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.events.RiskEvaluatedEvent"
            }
    )
    public void handleRiskEvaluated(RiskEvaluatedEvent event) {

        System.out.println("Orchestrator received RiskEvaluatedEvent for policyId: "
                + event.getPolicyId());

        if (processedEventRepository.existsById(event.getEventId())) {
            System.out.println("Duplicate RiskEvaluatedEvent ignored: " + event.getEventId());
            return;
        }

        //For testing

//        if (true) {
//            throw new RuntimeException("Simulated failure for retry testing");
//        }

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
                    new ProcessPaymentCommand(event.getSagaID(), event.getPolicyId(), event.getPolicyType(), event.getPremiumAmount());

            commandProducer.sendProcessPaymentCommand(command);

        } else {

            saga.setCurrentStep(SagaStep.COMPLETED_REJECTED);
        }

        sagaRepository.save(saga);

        processedEventRepository.save(new ProcessedEvent(event.getEventId()));

        System.out.println("Saga updated for policyId: "
                + event.getPolicyId());
    }

    @KafkaListener(
            topics = "payment-completed",
            groupId = "saga-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.events.PaymentCompletedEvent"
            }
    )
    public void handlePaymentCompleted(PaymentCompletedEvent event) {

        System.out.println(
                "Orchestrator received PaymentCompletedEvent for policyId: "
                        + event.getPolicyId()
        );

        if (processedEventRepository.existsById(event.getEventId())) {
            System.out.println("Duplicate PaymentCompletedEvent ignored: " + event.getEventId());
            return;
        }

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
        processedEventRepository.save(new ProcessedEvent(event.getEventId()));
    }
}