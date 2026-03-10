package com.insurance.platform.saga.controller;

import com.insurance.platform.saga.domain.SagaInstance;
import com.insurance.platform.saga.messaging.command.CreatePolicyCommand;
import com.insurance.platform.saga.service.SagaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/saga")
public class SagaController {

    private final SagaService sagaService;

    public SagaController(SagaService sagaService) {
        this.sagaService = sagaService;
    }

    @PostMapping("/start-policy")
    public SagaInstance startPolicy(@RequestBody CreatePolicyCommand command) {

        return sagaService.startSaga(command);
    }
}