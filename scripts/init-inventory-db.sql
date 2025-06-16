CREATE DATABASE IF NOT EXISTS inventory_service_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE inventory_service_db;

CREATE TABLE IF NOT EXISTS Produtos (
                                        produto_id VARCHAR(36) PRIMARY KEY,
                                        imagem_url VARCHAR(255),
                                        nome VARCHAR(255) NOT NULL,
                                        descricao TEXT,
                                        valor DECIMAL(10, 2) NOT NULL,
                                        quantidade INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Reservas (
                                        reserva_id VARCHAR(36) PRIMARY KEY,
                                        order_id VARCHAR(36) NOT NULL,
                                        produto_id VARCHAR(36) NOT NULL,
                                        quantidade_reservada INT NOT NULL,
                                        status ENUM('RESERVADO', 'CANCELADO') NOT NULL,
                                        FOREIGN KEY (produto_id) REFERENCES Produtos(produto_id)
);

CREATE TABLE IF NOT EXISTS Processed_Orders (
                                                order_id VARCHAR(36) PRIMARY KEY,
                                                processamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);