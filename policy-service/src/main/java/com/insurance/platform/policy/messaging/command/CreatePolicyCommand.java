package com.insurance.platform.policy.messaging.command;


import java.math.BigDecimal;

public class CreatePolicyCommand {

    private Long customerId;
    private String policyType;
    private BigDecimal premiumAmount;
    private String sagaId;

    public CreatePolicyCommand() {
    }

    public CreatePolicyCommand(String sagaId, Long customerId, String policyType, BigDecimal premiumAmount) {
        this.sagaId = sagaId;
        this.customerId = customerId;
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
    }

    public Long getCustomerId() {
        return customerId;
    }


    public String getPolicyType() {
        return policyType;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public void setPremiumAmount(BigDecimal premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public String getSagaId() {
        return sagaId;
    }
}
