package com.taniele.orderservice.dto;

/**
 * DTO do item para a mensagem do Kafka.
 */
public class KafkaOrderItemDTO {
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