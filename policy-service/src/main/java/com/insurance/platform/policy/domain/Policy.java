package com.insurance.platform.policy.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String policyNumber;

    private Long customerId;

    private String policyType; // MOTOR, HEALTH, etc.

    private BigDecimal premiumAmount;

    @Enumerated(EnumType.STRING)
    private PolicyStatus status; // Replaces String

    private LocalDateTime createdAt;

    // Default constructor
    public Policy() {
        this.createdAt = LocalDateTime.now();
        this.status = PolicyStatus.CREATED; // default status
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getPolicyType() { return policyType; }
    public void setPolicyType(String policyType) { this.policyType = policyType; }

    public BigDecimal getPremiumAmount() { return premiumAmount; }
    public void setPremiumAmount(BigDecimal premiumAmount) { this.premiumAmount = premiumAmount; }

    public PolicyStatus getStatus() { return status; }
    public void setStatus(PolicyStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
