package com.insurance.platform.saga.messaging.websocket;

import com.insurance.platform.saga.messaging.websocket.dto.SagaStatusUpdate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SagaWebSocketPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public SagaWebSocketPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendUpdate(String userId, SagaStatusUpdate update) {

        String destination = "/topic/saga/" + userId;

        System.out.println("====================================");
        System.out.println("Sending WebSocket update to: " + destination);
        System.out.println("Payload: " + update);
        System.out.println("====================================");

        messagingTemplate.convertAndSend(destination, update);
    }
}
