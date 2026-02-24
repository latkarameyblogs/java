package com.insurance.platform.policy.messaging.listener;

import com.insurance.platform.policy.domain.Policy;
import com.insurance.platform.policy.domain.PolicyStatus;
import com.insurance.platform.policy.messaging.command.ActivatePolicyCommand;
import com.insurance.platform.policy.repository.PolicyRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PolicyCommandListener {

    private final PolicyRepository repository;

    public PolicyCommandListener(PolicyRepository repository) {
        this.repository = repository;
    }


    @KafkaListener(
            topics = "activate-policy-command",
            groupId = "policy-group",
            properties = {
                    "spring.json.value.default.type=com.insurance.platform.policy.messaging.command.ActivatePolicyCommand"
            }
    )
    public void handleActivatePolicy(ActivatePolicyCommand command) {

        System.out.println(
                "Policy Service received ActivatePolicyCommand for policyId: "
                        + command.getPolicyId()
        );


        Policy policy =
                repository.findById(command.getPolicyId())
                        .orElseThrow();

        policy.setStatus(PolicyStatus.ACTIVE);

        repository.save(policy);


        System.out.println(
                "Policy ACTIVATED for policyId: "
                        + command.getPolicyId()
        );
    }
}