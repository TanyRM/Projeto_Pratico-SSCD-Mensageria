package com.taniele.orderservice.dto;

import java.util.List;

/**
 * DTO para receber a requisição de criação de pedido do frontend.
 */
public class OrderRequestDTO {
    private CustomerDTO customer;
    private List<OrderItemDTO> items;

    // Getters e Setters
    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}