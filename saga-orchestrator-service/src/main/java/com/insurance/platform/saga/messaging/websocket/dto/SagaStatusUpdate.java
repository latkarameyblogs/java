package com.insurance.platform.saga.messaging.websocket.dto;

import com.insurance.platform.saga.domain.SagaStatus;
import com.insurance.platform.saga.domain.SagaStep;

public class SagaStatusUpdate {

    private String eventId;
    private String sagaId;
    private Long policyId;

    private SagaStep step;
    private SagaStatus status;

    private String message;
    private long timestamp;

    public SagaStatusUpdate(
            String eventId,
            String sagaId,
            Long policyId,
            SagaStep step,
            SagaStatus status,
            String message
    ) {
        this.eventId = eventId;
        this.sagaId = sagaId;
        this.policyId = policyId;
        this.step = step;
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public SagaStep getStep() {
        return step;
    }

    public void setStep(SagaStep step) {
        this.step = step;
    }

    public SagaStatus getStatus() {
        return status;
    }

    public void setStatus(SagaStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}