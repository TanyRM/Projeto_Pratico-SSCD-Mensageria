package com.taniele.inventoryservice.Service;

import com.taniele.inventoryservice.dto.InventoryEventDTO;
import com.taniele.inventoryservice.dto.OrderDTO;
import com.taniele.inventoryservice.dto.OrderItemDTO;
import com.taniele.inventoryservice.model.ProcessedOrder;
import com.taniele.inventoryservice.model.Produto;
import com.taniele.inventoryservice.model.Reserva;
import com.taniele.inventoryservice.repository.ProcessedOrderRepository;
import com.taniele.inventoryservice.repository.ProdutoRepository;
import com.taniele.inventoryservice.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class InventoryService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private ProcessedOrderRepository processedOrderRepository;
    @Autowired
    private KafkaTemplate<String, InventoryEventDTO> kafkaTemplate;

    @Transactional
    public void processOrder(OrderDTO order) {
        // Garantir idempotência
        if (processedOrderRepository.existsById(order.getOrderId())) {
            System.out.println("Pedido " + order.getOrderId() + " já processado");
            return;
        }

        boolean allItemsAvailable = true;
        // Verifica estoque
        for (OrderItemDTO item : order.getItems()) {
            if (!produtoRepository.existsByProdutoIdAndQuantidadeGreaterThanEqual(item.getProdutoId(), item.getQuantidade())) {
                allItemsAvailable = false;
                break;
            }
        }

        // Processa resultado
        if (allItemsAvailable) {
            // baixar a quantidade e criar reservas se tiver estoque
            for (OrderItemDTO item : order.getItems()) {
                Produto p = produtoRepository.findById(item.getProdutoId()).get();
                p.setQuantidade(p.getQuantidade() - item.getQuantidade());
                produtoRepository.save(p);

                Reserva novaReserva = new Reserva();

                novaReserva.setReservaId("res-" + UUID.randomUUID().toString());
                novaReserva.setOrderId(order.getOrderId());
                novaReserva.setProdutoId(item.getProdutoId());
                novaReserva.setQuantidadeReservada(item.getQuantidade());
                novaReserva.setStatus("RESERVADO");

                reservaRepository.save(novaReserva);
                System.out.println("Reserva criada para o produto: " + item.getProdutoId() + " no pedido: " + order.getOrderId());
            }
            publishResult(order.getOrderId(), "SUCESSO");
        } else {
            // publicar falha se não tiver estoque
            publishResult(order.getOrderId(), "FALHA_ESTOQUE");
        }

        // Marca o pedido como processado
        processedOrderRepository.save(new ProcessedOrder(order.getOrderId()));
    }

    private void publishResult(String orderId, String status) {
        InventoryEventDTO event = new InventoryEventDTO(orderId, status);
        // Publicar resultado no inventory events
        kafkaTemplate.send("inventory-events", event);
        System.out.println("Resultado publicado para o pedido " + orderId + ": " + status);
    }
}