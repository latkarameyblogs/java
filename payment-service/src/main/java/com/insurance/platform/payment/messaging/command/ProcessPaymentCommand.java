package com.insurance.platform.payment.messaging.command;

public class ProcessPaymentCommand {

    private Long policyId;

    public ProcessPaymentCommand() {
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }
}