package com.taniele.inventoryservice.repository;

import com.taniele.inventoryservice.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Produto.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {
    boolean existsByProdutoIdAndQuantidadeGreaterThanEqual(String produtoId, int quantidade);
}
