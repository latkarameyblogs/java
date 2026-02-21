package com.insurance.platform.underwriting.event;

import java.math.BigDecimal;

public class PolicyCreatedEvent {

    private Long policyId;
    private Long customerId;
    private String policyType;
    private BigDecimal premiumAmount;

    public PolicyCreatedEvent() {
    }

    public PolicyCreatedEvent(Long policyId, Long customerId, String policyType, BigDecimal premiumAmount) {
        this.policyId = policyId;
        this.customerId = customerId;
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
    }

    public Long getPolicyId() {
        return policyId;
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
}