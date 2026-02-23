package com.insurance.platform.saga.repository;

import com.insurance.platform.saga.domain.SagaInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SagaRepository extends JpaRepository<SagaInstance, UUID> {

    Optional<SagaInstance> findByPolicyId(Long policyId);
}