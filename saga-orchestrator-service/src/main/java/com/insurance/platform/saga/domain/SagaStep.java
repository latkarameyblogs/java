
package com.insurance.platform.saga.domain;

public enum SagaStep {

    WAITING_FOR_RISK,
    WAITING_FOR_PAYMENT,
    COMPLETED_SUCCESS,
    COMPLETED_REJECTED,
    COMPLETED_CANCELLED,
    POLICY_CREATION_STARTED
}
