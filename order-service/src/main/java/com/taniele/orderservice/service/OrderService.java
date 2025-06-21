package com.taniele.orderservice.service;

import com.taniele.orderservice.dto.*;
import com.taniele.orderservice.model.Customer;
import com.taniele.orderservice.model.Order;
import com.taniele.orderservice.model.OrderItem;
import com.taniele.orderservice.repository.CustomerRepository;
import com.taniele.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private KafkaTemplate<String, KafkaOrderDTO> kafkaTemplate;

    @Transactional
    public Order createOrder(OrderRequestDTO requestDTO) {
        Customer customer = customerRepository.findByEmail(requestDTO.getCustomer().getEmail())
                .orElseGet(() -> {
                    Customer newCustomer = new Customer();
                    newCustomer.setCustomerId("cust-" + UUID.randomUUID().toString());
                    newCustomer.setNome(requestDTO.getCustomer().getNome());
                    newCustomer.setEmail(requestDTO.getCustomer().getEmail());
                    newCustomer.setTelefone(requestDTO.getCustomer().getTelefone());
                    return customerRepository.save(newCustomer);
                });

        Order order = new Order();
        order.setOrderId("ord-" + UUID.randomUUID().toString());
        order.setCustomerId(customer.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDENTE");

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;
        for(OrderItemDTO itemDTO : requestDTO.getItems()) {
            OrderItem item = new OrderItem();
            item.setItemId("item-" + UUID.randomUUID().toString());
            item.setProdutoId(itemDTO.getProdutoId());
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnidade(itemDTO.getPrecoUnidade());
            item.setOrder(order);
            orderItems.add(item);
            valorTotal = valorTotal.add(itemDTO.getPrecoUnidade().multiply(new BigDecimal(itemDTO.getQuantidade())));
        }
        order.setItems(orderItems);
        order.setValorTotal(valorTotal);

        Order savedOrder = orderRepository.save(order);
        publishOrderCreatedEvent(savedOrder);

        return savedOrder;
    }

    @Transactional
    public void updateOrderStatus(InventoryEventDTO event) {
        System.out.println("Recebido evento de inventário para o pedido: " + event.getOrderId());
        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            if("SUCESSO".equals(event.getStatus())) {
                order.setStatus("APROVADO");
            } else {
                order.setStatus("REJEITADO");
            }
            orderRepository.save(order);
            System.out.println("Status do pedido " + order.getOrderId() + " atualizado para: " + order.getStatus());
        });
    }

    private void publishOrderCreatedEvent(Order order) {
        KafkaOrderDTO kafkaOrderDTO = new KafkaOrderDTO();
        kafkaOrderDTO.setOrderId(order.getOrderId());

        List<KafkaOrderItemDTO> kafkaItems = order.getItems().stream().map(item -> {
            KafkaOrderItemDTO kafkaItemDTO = new KafkaOrderItemDTO();
            kafkaItemDTO.setProdutoId(item.getProdutoId());
            kafkaItemDTO.setQuantidade(item.getQuantidade());
            return kafkaItemDTO;
        }).collect(Collectors.toList());

        kafkaOrderDTO.setItems(kafkaItems);

        System.out.println("Publicando pedido no tópico 'orders': " + kafkaOrderDTO.getOrderId());
        kafkaTemplate.send("orders", kafkaOrderDTO);
    }
}
