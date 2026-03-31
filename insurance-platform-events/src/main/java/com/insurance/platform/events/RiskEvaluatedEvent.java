package com.insurance.platform.events;

import java.math.BigDecimal;

public class RiskEvaluatedEvent implements SagaEvent {

    private Long policyId;
    private boolean approved;
    private String reason;
    private String sagaId;


    private String policyType;

    private BigDecimal premiumAmount;

    public RiskEvaluatedEvent() {
    }

    public RiskEvaluatedEvent(String sagaId, Long policyId, boolean approved, String reason, String policyType, BigDecimal premiumAmount,String userId){
        this.sagaId = sagaId;
        this.policyId = policyId;
        this.approved = approved;
        this.reason = reason;
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
        this.userId =userId;
    }



    public String getEventId() {
        return eventId;
    }

    public String getSagaId() {
        return sagaId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    private String eventId;

    public Long getPolicyId() {
        return policyId;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getReason() {
        return reason;
    }


    public String getPolicyType() {
        return policyType;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;
}