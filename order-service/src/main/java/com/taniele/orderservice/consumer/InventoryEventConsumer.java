package com.taniele.orderservice.consumer;

import com.taniele.orderservice.dto.InventoryEventDTO;
import com.taniele.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventConsumer {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "inventory-events", groupId = "order-group", containerFactory = "inventoryEventContainerFactory")
    public void consume(InventoryEventDTO event) {
        orderService.updateOrderStatus(event);
    }
}