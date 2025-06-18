package com.taniele.inventoryservice.dto;

/**
 * DTO para receber os dados do pedido do tópico 'orders'.
 */
public class OrderDTO {
    private String orderId;
    private java.util.List<OrderItemDTO> items;

    // Getters e Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public java.util.List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(java.util.List<OrderItemDTO> items) {
        this.items = items;
    }
}
