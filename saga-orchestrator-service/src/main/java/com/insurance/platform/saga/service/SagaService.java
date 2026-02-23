package com.insurance.platform.saga.service;

import com.insurance.platform.saga.domain.SagaInstance;
import com.insurance.platform.saga.domain.SagaStep;
import com.insurance.platform.saga.repository.SagaRepository;
import org.springframework.stereotype.Service;

@Service
public class SagaService {

    private final SagaRepository sagaRepository;

    public SagaService(SagaRepository sagaRepository) {
        this.sagaRepository = sagaRepository;
    }

    public SagaInstance startSaga(Long policyId) {

        SagaInstance saga = new SagaInstance();
        saga.setPolicyId(policyId);
        saga.setCurrentStep(SagaStep.WAITING_FOR_RISK);

        return sagaRepository.save(saga);
    }
}