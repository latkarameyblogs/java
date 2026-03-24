package com.insurance.platform.saga.controller;

import com.insurance.platform.saga.domain.SagaInstance;
import com.insurance.platform.saga.messaging.command.CreatePolicyCommand;
import com.insurance.platform.saga.service.SagaService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/saga")
public class SagaController {

    private final SagaService sagaService;

    public SagaController(SagaService sagaService) {
        this.sagaService = sagaService;
    }

//    @PostMapping("/start-policy")
//    public SagaInstance startPolicy(@RequestBody CreatePolicyCommand command) {
//
//        return sagaService.startSaga(command);
//    }


    @GetMapping("/test")
    public Map<String,Object> test(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaims();
    }

    @PostMapping("/start-policy")
    public SagaInstance startPolicy(@RequestBody CreatePolicyCommand command,
                                    @AuthenticationPrincipal Jwt jwt) {

        command.setUserId(jwt.getSubject()); // 🔥 key line

        return sagaService.startSaga(command);
    }
}