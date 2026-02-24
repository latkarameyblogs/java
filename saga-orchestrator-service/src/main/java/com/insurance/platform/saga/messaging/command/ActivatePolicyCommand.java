package com.insurance.platform.saga.messaging.command;

public class ActivatePolicyCommand {

    private Long policyId;

    public ActivatePolicyCommand() {
    }

    public ActivatePolicyCommand(Long policyId) {
        this.policyId = policyId;
    }

    public Long getPolicyId() {
        return policyId;
    }
}