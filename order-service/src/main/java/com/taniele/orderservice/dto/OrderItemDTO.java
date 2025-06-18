package com.taniele.orderservice.dto;

import java.math.BigDecimal;

/**
 * DTO para os itens do pedido dentro da requisição.
 */
public class OrderItemDTO {
    private String produtoId;
    private int quantidade;
    private BigDecimal precoUnidade;

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

    public BigDecimal getPrecoUnidade() {
        return precoUnidade;
    }

    public void setPrecoUnidade(BigDecimal precoUnidade) {
        this.precoUnidade = precoUnidade;
    }
}