package com.insurance.platform.saga.messaging.command;

public class ActivatePolicyCommand {

    private Long policyId;

    private String userId;

    public ActivatePolicyCommand() {
    }

    public ActivatePolicyCommand(Long policyId,String userId) {
        this.policyId = policyId;
        this.userId = userId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }
}