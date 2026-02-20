package com.insurance.platform.underwriting.controller;

import com.insurance.platform.underwriting.dto.RiskEvaluationRequest;
import com.insurance.platform.underwriting.dto.RiskEvaluationResponse;
import com.insurance.platform.underwriting.service.UnderwritingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/underwriting")
public class UnderwritingController {

    private final UnderwritingService underwritingService;

    public UnderwritingController(UnderwritingService underwritingService) {
        this.underwritingService = underwritingService;
    }

    @PostMapping("/evaluate")
    public RiskEvaluationResponse evaluate(@Valid @RequestBody RiskEvaluationRequest request) {
        return underwritingService.evaluateRisk(request);
    }
}