package com.taniele.inventoryservice.dto;

/**
 * DTO para publicar o resultado do processamento no tópico 'inventory-events'.
 */
public class InventoryEventDTO {
    private String orderId;
    private String status; // "SUCESSO" ou "FALHA_ESTOQUE"

    public InventoryEventDTO() {
    }

    public InventoryEventDTO(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

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
}
