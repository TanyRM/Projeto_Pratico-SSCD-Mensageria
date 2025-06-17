package com.taniele.notificationservice.service;

import com.taniele.notificationservice.dto.InventoryEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryEventConsumer.class);

    @KafkaListener(topics = "inventory-events", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(InventoryEventDTO event) {
        log.info("Evento de inventário recebido: {}", event);

        // Simular envio de email/SMS
        if("SUCESSO".equals(event.getStatus())) {
            log.info("--- Pedido {} confirmado! Estoque reservado com sucesso.", event.getOrderId());
        } else {
            log.warn("--- Pedido {} falhou por {}", event.getOrderId(), event.getStatus());
        }
    }
}
