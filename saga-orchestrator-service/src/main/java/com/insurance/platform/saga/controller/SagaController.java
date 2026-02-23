package com.insurance.platform.saga.controller;

import com.insurance.platform.saga.domain.SagaInstance;
import com.insurance.platform.saga.service.SagaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/saga")
public class SagaController {

    private final SagaService sagaService;

    public SagaController(SagaService sagaService) {
        this.sagaService = sagaService;
    }

    @PostMapping("/start/{policyId}")
    public SagaInstance startSaga(@PathVariable Long policyId) {
        return sagaService.startSaga(policyId);
    }
}