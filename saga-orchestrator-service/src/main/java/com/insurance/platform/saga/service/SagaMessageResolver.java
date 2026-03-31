package com.insurance.platform.saga.service;

import com.insurance.platform.saga.domain.SagaStatus;
import com.insurance.platform.saga.domain.SagaStep;
import org.springframework.stereotype.Component;

@Component
public class SagaMessageResolver {

    public String resolve(SagaStep step, SagaStatus status) {

        switch (step) {

            case POLICY_CREATION_STARTED:
                return "Policy creation started";

            case WAITING_FOR_RISK:
                return "Risk evaluation in progress";

            case WAITING_FOR_PAYMENT:
                return "Payment is being processed";

            case COMPLETED_SUCCESS:
                return "Policy successfully activated";

            case COMPLETED_REJECTED:
                return "Policy rejected due to risk";

            case COMPLETED_CANCELLED:
                return "Policy cancelled due to payment failure";

            default:
                return "Processing...";
        }
    }
}
