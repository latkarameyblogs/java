package com.insurance.platform.policy.event;

import java.math.BigDecimal;

public class PolicyCreatedEvent {

    private Long policyId;
    private Long customerId;
    private String policyType;
    private BigDecimal premiumAmount;
    private String sagaId;

    public PolicyCreatedEvent() {
    }

    public PolicyCreatedEvent(String sagaId,Long policyId, Long customerId, String policyType, BigDecimal premiumAmount) {
        this.sagaId = sagaId;
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

    public String getSagaId() {
        return sagaId;
    }


}