package com.insurance.platform.policy.client.dto;

import java.math.BigDecimal;

public class PaymentRequest {

    private Long customerId;
    private BigDecimal amount;

    public PaymentRequest() {
    }

    public PaymentRequest(Long customerId, BigDecimal amount) {
        this.customerId = customerId;
        this.amount = amount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}