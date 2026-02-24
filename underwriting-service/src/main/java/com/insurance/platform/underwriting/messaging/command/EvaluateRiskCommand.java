package com.insurance.platform.underwriting.messaging.command;

public class EvaluateRiskCommand {

    private Long policyId;

    public EvaluateRiskCommand() {
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }
}