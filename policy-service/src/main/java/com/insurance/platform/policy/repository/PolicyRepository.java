package com.insurance.platform.policy.repository;

import com.insurance.platform.policy.domain.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    Optional<Policy> findByPolicyNumber(String policyNumber);
}
