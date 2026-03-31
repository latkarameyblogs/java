package com.insurance.platform.events;

import java.math.BigDecimal;

public class PolicyCreatedEvent implements SagaEvent{

    private Long policyId;
    private Long customerId;
    private String policyType;
    private BigDecimal premiumAmount;
    private String sagaId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    private String eventId;

    public PolicyCreatedEvent() {
    }

    public PolicyCreatedEvent(String sagaId, Long policyId, Long customerId, String policyType, BigDecimal premiumAmount) {
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