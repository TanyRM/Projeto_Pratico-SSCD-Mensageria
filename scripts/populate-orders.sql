USE order_service_db;

DELETE FROM Order_Items;
DELETE FROM Orders;
DELETE FROM Customers;

INSERT INTO Customers (customer_id, nome, email, telefone) VALUES
                                                               ('cust-001', 'Ana Silva', 'ana.silva@email.com', '62988887777'),
                                                               ('cust-002', 'Bruno Costa', 'bruno.costa@email.com', '62999998888');


INSERT INTO Orders (order_id, customer_id, order_date, status, valor_total) VALUES
    ('order-001', 'cust-001', '2025-06-10 14:30:00', 'APROVADO', 1050.75);

INSERT INTO Order_Items (item_id, order_id, produto_id, quantidade, preco_unidade) VALUES
                                                                                       ('item-001', 'order-001', 'prod-002', 1, 250.75), -- Mouse
                                                                                       ('item-002', 'order-001', 'prod-003', 1, 450.00), -- Teclado
                                                                                       ('item-003', 'order-001', 'prod-006', 1, 350.00); -- Headset


INSERT INTO Orders (order_id, customer_id, order_date, status, valor_total) VALUES
    ('order-002', 'cust-002', CURRENT_TIMESTAMP, 'PENDENTE', 6150.00);

-- Itens do PEDIDO 2
INSERT INTO Order_Items (item_id, order_id, produto_id, quantidade, preco_unidade) VALUES
                                                                                       ('item-004', 'order-002', 'prod-001', 1, 5500.00), -- Laptop
                                                                                       ('item-005', 'order-002', 'prod-007', 1, 650.00); -- SSD