package com.insurance.platform.events;

public interface SagaEvent {
    String getEventId();
    String getSagaId();
    Long getPolicyId();
    String getUserId();
}
