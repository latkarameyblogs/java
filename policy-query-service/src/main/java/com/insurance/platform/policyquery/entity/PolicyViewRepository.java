package com.insurance.platform.policyquery.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyViewRepository extends JpaRepository<PolicyView, Long> {

    List<PolicyView> findByUserId(String userId);
}
