package com.insurance.platform.saga.messaging.command;

public class EvaluateRiskCommand {

    private Long policyId;

    public EvaluateRiskCommand() {
    }

    public EvaluateRiskCommand(Long policyId) {
        this.policyId = policyId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }
}