package com.taniele.inventoryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Mapeia a tabela 'Produtos'.
 */
@Entity
@Table(name = "Produtos")
public class Produto {
    @Id
    private String produtoId;
    private String imagemUrl;
    private String nome;
    private String descricao;
    private java.math.BigDecimal valor;
    private int quantidade;

    // Getters e Setters
    public String getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(String produtoId) {
        this.produtoId = produtoId;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public java.math.BigDecimal getValor() {
        return valor;
    }

    public void setValor(java.math.BigDecimal valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
