package com.insurance.platform.policy.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_events")
public class ProcessedEvent {

    @Id
    private String eventId;

    private LocalDateTime processedAt = LocalDateTime.now();

}