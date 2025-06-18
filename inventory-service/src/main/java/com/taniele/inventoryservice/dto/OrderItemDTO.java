package com.taniele.inventoryservice.dto;

/**
 * DTO para receber os dados de um item do pedido do tópico 'orders'.
 */
public class OrderItemDTO {
    private String produtoId;
    private int quantidade;

    // Getters e Setters
    public String getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(String produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
