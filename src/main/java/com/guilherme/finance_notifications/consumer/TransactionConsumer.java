package com.guilherme.finance_notifications.consumer;

import com.guilherme.finance_notifications.dto.TransactionEvent;
import com.guilherme.finance_notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void consume(TransactionEvent event) {
        log.info("📨 Evento recebido da fila: transação '{}' de {} para usuário {}",
                event.getDescription(),
                event.getAmount(),
                event.getUserEmail());

        notificationService.processNotification(event);
    }
}