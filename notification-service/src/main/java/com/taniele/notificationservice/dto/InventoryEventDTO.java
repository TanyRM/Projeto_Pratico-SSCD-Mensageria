package com.taniele.notificationservice.dto;

/**
 * DTO para receber o evento do tópico 'inventory-events'.
 * É uma cópia da classe do inventory-service, pois os módulos são independentes.
 */
public class InventoryEventDTO {
    private String orderId;
    private String status;

    // Getters e Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InventoryEventDTO{" +
                "orderId='" + orderId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
