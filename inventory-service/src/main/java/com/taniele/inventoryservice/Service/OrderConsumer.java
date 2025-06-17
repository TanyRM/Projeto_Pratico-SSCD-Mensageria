package com.taniele.inventoryservice.Service;

import com.taniele.inventoryservice.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private final InventoryService inventoryService;

    @Autowired
    public OrderConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "orders", groupId = "inventory-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(OrderDTO order) {
        System.out.println("Novo pedido recebido para processamento: " + order.getOrderId());
        inventoryService.processOrder(order);
    }
}