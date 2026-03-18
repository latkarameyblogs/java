package com.insurance.platform.policy.messaging.command;

public class ActivatePolicyCommand {

    private Long policyId;

    public ActivatePolicyCommand() {
    }

    public Long getPolicyId() {
        return policyId;
    }
}