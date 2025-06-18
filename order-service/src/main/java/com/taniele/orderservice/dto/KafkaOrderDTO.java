package com.taniele.orderservice.dto;

import java.util.List;

/**
 * DTO para a mensagem que será publicada no Kafka.
 */
public class KafkaOrderDTO {
    private String orderId;
    private List<KafkaOrderItemDTO> items;

    // Getters e Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<KafkaOrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<KafkaOrderItemDTO> items) {
        this.items = items;
    }
}