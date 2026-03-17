package com.insurance.platform.saga.repository;


import com.insurance.platform.saga.messaging.event.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {
}