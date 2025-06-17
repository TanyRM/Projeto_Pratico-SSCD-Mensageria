package com.taniele.inventoryservice.repository;

import com.taniele.inventoryservice.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Reserva.
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, String> {
}
