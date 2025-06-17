package com.taniele.inventoryservice.repository;

import com.taniele.inventoryservice.model.ProcessedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade ProcessedOrder.
 */
@Repository
public interface ProcessedOrderRepository extends JpaRepository<ProcessedOrder, String> {
}
