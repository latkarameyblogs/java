package com.insurance.platform.underwriting.messaging.listener;

import com.insurance.platform.underwriting.messaging.command.EvaluateRiskCommand;
import com.insurance.platform.underwriting.service.UnderwritingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EvaluateRiskCommandListener {

    private final UnderwritingService underwritingService;

    public EvaluateRiskCommandListener(UnderwritingService underwritingService) {
        this.underwritingService = underwritingService;
    }

    @KafkaListener(
            topics = "evaluate-risk-command",
            groupId = "underwriting-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.underwriting.messaging.command.EvaluateRiskCommand"
            }
    )
    public void handleEvaluateRiskCommand(EvaluateRiskCommand command) {

        System.out.println(
                "Underwriting Service received EvaluateRiskCommand for policyId: "
                        + command.getPolicyId()
        );

        underwritingService.evaluateRiskForPolicy(
                command.getPolicyId(),
                command.getPolicyType(),
                command.getPremiumAmount(),
                command.getSagaId()
        );

        System.out.println(
                "Risk evaluation initiated for policyId: "
                        + command.getPolicyId()
        );
    }
}