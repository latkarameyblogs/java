package com.insurance.platform.policyquery.listener;

import com.insurance.platform.policyquery.entity.PolicyView;
import com.insurance.platform.policyquery.entity.PolicyViewRepository;
import com.insurance.platform.policyquery.event.PaymentCompletedEvent;
import com.insurance.platform.policyquery.event.PolicyCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PolicyEventListener {

    private final PolicyViewRepository repository;

    public PolicyEventListener(PolicyViewRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = "policy-created",
            groupId = "policy-query-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.policyquery.event.PolicyCreatedEvent"
            }
    )
    public void handlePolicyCreated(PolicyCreatedEvent event) {

        System.out.println("Received PolicyCreatedEvent in query service");

        PolicyView view = new PolicyView();
        view.setPolicyId(event.getPolicyId());
        view.setCustomerId(event.getCustomerId());
        view.setPolicyType(event.getPolicyType());
        view.setPremiumAmount(event.getPremiumAmount());
        view.setStatus("UNDER_REVIEW");

        repository.save(view);
    }


    @KafkaListener(
            topics = "payment-completed",
            groupId = "policy-query-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.policyquery.event.PaymentCompletedEvent"
            }
    )
    public void handlePaymentCompleted(PaymentCompletedEvent event) {

        System.out.println("Received PaymentCompletedEvent in query service");

        PolicyView view = repository.findById(event.getPolicyId())
                .orElseThrow();

        if (event.isSuccess()) {
            view.setStatus("ACTIVE");
        } else {
            view.setStatus("CANCELLED");
        }

        repository.save(view);
    }
}