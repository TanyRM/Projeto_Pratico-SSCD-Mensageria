package com.taniele.inventoryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Mapeia a tabela 'Processed_Orders' para garantir a idempotência.
 */
@Entity
@Table(name = "Processed_Orders")
public class ProcessedOrder {
    @Id
    private String orderId;
    private java.time.LocalDateTime processamento;

    public ProcessedOrder() {
    }

    public ProcessedOrder(String orderId) {
        this.orderId = orderId;
        this.processamento = java.time.LocalDateTime.now();
    }

    // Getters e Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public java.time.LocalDateTime getProcessamento() {
        return processamento;
    }

    public void setProcessamento(java.time.LocalDateTime processamento) {
        this.processamento = processamento;
    }
}
