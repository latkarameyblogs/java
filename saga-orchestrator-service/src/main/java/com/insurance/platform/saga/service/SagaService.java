package com.insurance.platform.saga.service;

import com.insurance.platform.saga.domain.SagaInstance;
import com.insurance.platform.saga.domain.SagaStep;
import com.insurance.platform.saga.messaging.command.EvaluateRiskCommand;
import com.insurance.platform.saga.messaging.producer.SagaCommandProducer;
import com.insurance.platform.saga.repository.SagaRepository;
import org.springframework.stereotype.Service;

@Service
public class SagaService {

    private final SagaRepository sagaRepository;

    private final SagaCommandProducer commandProducer;

    public SagaService(SagaRepository sagaRepository,
                       SagaCommandProducer commandProducer) {
        this.sagaRepository = sagaRepository;
        this.commandProducer = commandProducer;
    }

    public SagaInstance startSaga(Long policyId) {

        SagaInstance saga = new SagaInstance();
        saga.setPolicyId(policyId);
        saga.setCurrentStep(SagaStep.WAITING_FOR_RISK);

        SagaInstance savedSaga = sagaRepository.save(saga);

        // publish command
        EvaluateRiskCommand command =
                new EvaluateRiskCommand(policyId);

        commandProducer.sendEvaluateRiskCommand(command);

        return savedSaga;
    }
}