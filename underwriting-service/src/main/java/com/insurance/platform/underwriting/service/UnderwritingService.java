package com.insurance.platform.underwriting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.platform.underwriting.dto.RiskEvaluationRequest;
import com.insurance.platform.underwriting.dto.RiskEvaluationResponse;
import com.insurance.platform.underwriting.event.RiskEvaluatedEvent;
import com.insurance.platform.underwriting.outbox.OutboxEvent;
import com.insurance.platform.underwriting.outbox.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UnderwritingService {

    private final OutboxEventRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public UnderwritingService(OutboxEventRepository outboxRepository,
                               ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    // EXISTING BUSINESS LOGIC (UNCHANGED)
    public RiskEvaluationResponse evaluateRisk(RiskEvaluationRequest request) {

        BigDecimal premium = request.getPremiumAmount();
        String policyType = request.getPolicyType();

        if ("HEALTH".equalsIgnoreCase(policyType)) {
            if (premium.compareTo(BigDecimal.valueOf(10000)) > 0) {
                return new RiskEvaluationResponse(false, "HIGH");
            }
            return new RiskEvaluationResponse(true, "MEDIUM");
        }

        if ("MOTOR".equalsIgnoreCase(policyType)) {
            if (premium.compareTo(BigDecimal.valueOf(50000)) > 0) {
                return new RiskEvaluationResponse(false, "HIGH");
            }
            return new RiskEvaluationResponse(true, "LOW");
        }

        return new RiskEvaluationResponse(true, "LOW");
    }


    // NEW METHOD FOR SAGA COMMAND HANDLING
    @Transactional
    public void evaluateRiskForPolicy(Long policyId,
                                      String policyType,
                                      BigDecimal premiumAmount,
                                      String sagaId) {

        RiskEvaluationRequest request =
                new RiskEvaluationRequest(policyType, premiumAmount);

        RiskEvaluationResponse response =
                evaluateRisk(request);

        RiskEvaluatedEvent event =
                new RiskEvaluatedEvent(
                        sagaId,
                        policyId,
                        response.isApproved(),
                        response.getRiskCategory(),
                        policyType,
                        premiumAmount
                );
        event.setEventId(UUID.randomUUID().toString());
        try {

            String payload =
                    objectMapper.writeValueAsString(event);

            OutboxEvent outboxEvent =
                    new OutboxEvent(
                            "UNDERWRITING",
                            policyId.toString(),
                            "RISK_EVALUATED",
                            payload
                    );

            outboxRepository.save(outboxEvent);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}