package com.insurance.platform.policy.messaging.listener;

import com.insurance.platform.policy.domain.Policy;
import com.insurance.platform.policy.messaging.command.ActivatePolicyCommand;
import com.insurance.platform.policy.messaging.command.CreatePolicyCommand;
import com.insurance.platform.policy.service.PolicyService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PolicyCommandListener {

    public PolicyCommandListener(PolicyService policyService) {
        this.policyService = policyService;
    }

    private final PolicyService policyService;


    @KafkaListener(topics = "create-policy-command", groupId = "policy-service",  properties = {
            "spring.json.value.default.type=com.insurance.platform.policy.messaging.command.CreatePolicyCommand"
    })
    public void handleCreatePolicy(CreatePolicyCommand command) {

        Policy policy = new Policy();

        policy.setCustomerId(command.getCustomerId());
        policy.setPolicyType(command.getPolicyType());
        policy.setPremiumAmount(command.getPremiumAmount());

        policyService.createPolicy(policy,command.getSagaId());
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

        policyService.activatePolicy(command.getPolicyId());
    }
}