package com.insurance.platform.saga.messaging.websocket;

import com.insurance.platform.events.SagaEvent;
import com.insurance.platform.saga.domain.SagaInstance;
import com.insurance.platform.saga.messaging.websocket.dto.SagaStatusUpdate;
import com.insurance.platform.saga.service.SagaMessageResolver;
import org.springframework.stereotype.Service;

@Service
public class SagaNotificationService {

    private final SagaWebSocketPublisher webSocketPublisher;
    private final SagaMessageResolver messageResolver;

    public SagaNotificationService(
            SagaWebSocketPublisher webSocketPublisher,
            SagaMessageResolver messageResolver
    ) {
        this.webSocketPublisher = webSocketPublisher;
        this.messageResolver = messageResolver;
    }

    public void notifyUser(SagaEvent event, SagaInstance saga) {

        String message = messageResolver.resolve(
                saga.getCurrentStep(),
                saga.getStatus()
        );

        SagaStatusUpdate update = new SagaStatusUpdate(
                event.getEventId(),
                saga.getId().toString(),
                saga.getPolicyId(),
                saga.getCurrentStep(),
                saga.getStatus(),
                message
        );

        webSocketPublisher.sendUpdate(event.getUserId(), update);
    }
}