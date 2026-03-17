package com.insurance.platform.saga.messaging.event;

import java.math.BigDecimal;

public class RiskEvaluatedEvent {

    private Long policyId;
    private boolean approved;
    private String reason;
    private String sagaID;



    private String policyType;

    private BigDecimal premiumAmount;

    private String eventId;



    public RiskEvaluatedEvent() {}

    public RiskEvaluatedEvent(String sagaID,Long policyId, boolean approved, String reason, String policyType, BigDecimal premiumAmount) {
        this.sagaID = sagaID;
        this.policyId = policyId;
        this.approved = approved;
        this.reason = reason;
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getReason() {
        return reason;
    }

    public String getSagaID() {
        return sagaID;
    }

    public String getPolicyType() {
        return policyType;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}