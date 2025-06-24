package com.taniele.orderservice.controller;

import com.taniele.orderservice.dto.OrderRequestDTO;
import com.taniele.orderservice.dto.OrderResponseDTO;
import com.taniele.orderservice.model.Order;
import com.taniele.orderservice.repository.OrderRepository;
import com.taniele.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            Order newOrder = orderService.createOrder(orderRequestDTO);
            OrderResponseDTO response = new OrderResponseDTO(
                    newOrder.getOrderId(),
                    newOrder.getStatus(),
                    "Pedido recebido com sucesso e está sendo processado."
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(Exception e) {
            OrderResponseDTO errorResponse = new OrderResponseDTO(null, "ERRO", "Falha ao criar o pedido: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        return orderRepository.findById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}