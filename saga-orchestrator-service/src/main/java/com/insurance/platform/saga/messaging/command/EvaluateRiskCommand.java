package com.insurance.platform.saga.messaging.command;

import java.math.BigDecimal;

public class EvaluateRiskCommand {

    private Long policyId;

    private String policyType;

    private BigDecimal premiumAmount;

    private String sagaId;

    private String userId;

    public EvaluateRiskCommand() {
    }

    public EvaluateRiskCommand(String sagaId, Long policyId, String policyType, BigDecimal premiumAmount,String userId) {
        this.sagaId = sagaId;
        this.policyId = policyId;
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
        this.userId = userId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(BigDecimal premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}