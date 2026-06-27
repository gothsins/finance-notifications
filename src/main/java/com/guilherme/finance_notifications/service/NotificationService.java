package com.guilherme.finance_notifications.service;

import com.guilherme.finance_notifications.dto.TransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class NotificationService {

    public void processNotification(TransactionEvent event) {
        String emoji = event.getType().equals("INCOME") ? "💰" : "💸";

        log.info("=====================================");
        log.info("  NOTIFICAÇÃO DE TRANSAÇÃO");
        log.info("=====================================");
        log.info("  {} Tipo     : {}", emoji, event.getType());
        log.info("  📝 Descrição : {}", event.getDescription());
        log.info("  💲 Valor     : R$ {}", event.getAmount());
        log.info("  📅 Data      : {}", event.getDate());
        log.info("  🏷️  Categoria : {}", event.getCategoryName());
        log.info("  👤 Usuário   : {}", event.getUserEmail());
        log.info("  🕐 Processado: {}", LocalDateTime.now());
        log.info("=====================================");
    }
}