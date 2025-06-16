# Projeto: Sistema Distribuído com Kafka e Java

## Descrição
Sistema distribuído para plataforma de e-commerce, composto por três serviços integrados via Apache Kafka: Order-Service, Inventory-Service e Notification-Service.

## Integrantes
- Raquel Dias (Order-Service + Frontend)
- Taniele Rocha (Inventory-Service + Notification-Service)

## Arquitetura

- **Order-Service:** Produtor que expõe uma REST API e publica pedidos no tópico `orders`.
- **Inventory-Service:** Consumidor do tópico `orders`, reserva estoque e publica resultado no tópico `inventory-events`.
- **Notification-Service:** Consumidor do tópico `inventory-events`, simula envio de notificações.

### Diagrama de Fluxo
<!-- (se possível) Adicionar diagrama ilustrando o fluxo entre os serviços e os tópicos Kafka -->

## Tópicos Kafka

| Tópico            | Partições | Produtor        | Consumidor            | Descrição                        |
|-------------------|-----------|-----------------|-----------------------|----------------------------------|
| orders            | 1         | Order-Service   | Inventory-Service     | Pedidos confirmados              |
| inventory-events  | 1         | Inventory-Service| Notification-Service  | Resultado da reserva de estoque  |

mais informações em [Tópicos Kafka](./kafka-topics.md)

## Como Executar

1. **Instale o Apache Kafka** (de preferência a versão 2.13) em [Apache Kafka -Downloads](https://kafka.apache.org/downloads) e inicie o serviço do zookeeper e em seguida o kafka.

2. Instale um **servidor de banco de dados** MariaDB/MySQL em sua máquina.

3. **Crie os tópicos necessários:** </br>
   No prompt de comando navegue para a pasta do kafka e rode os comandos:
   ```
   .\bin\windows\kafka-topics.bat --create --topic inventory-events --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --config cleanup.policy=delete
   ```
   ```
   .\bin\windows\kafka-topics.bat --create --topic orders --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --config retention.ms=604800000
   ```

4. **Clone o repositório:** </br>
   git clone https://github.com/TanyRM/Projeto_Pratico-SSCD-Mensageria.git

5. **Crie e popule os bancos de dados**: </br>
   - Usando uma ferramenta como DBeaver ou MySQL Workbench, execute os seguintes scripts SQL localizados na pasta /scripts do projeto:
   - Primeiro, execute init-inventory-db.sql e init-order-db.sql
   - depois, execute populate-inventory.sql para criar o catálogo de produtos.
   - Em seguida, execute populate-orders.sql para adicionar clientes e pedidos de exemplo.

6. **Compile e execute cada serviço:** </br>
   - Order-Service (incluindo frontend)
   - Inventory-Service
   - Notification-Service

## Exemplos de Uso
- Realize um pedido através da interface
- Verifique os logs dos serviços para acompanhar o fluxo das mensagens.

## Requisitos Funcionais
- RF-1: Criação dos tópicos orders e inventory-events.
- RF-2: API REST para pedidos.
- RF-3: Processamento de pedidos e reserva de estoque.
- RF-4: Registro de notificações no console.

## Requisitos Não-Funcionais
- **Escalabilidade:** Descrever como usar aumentar partições e consumidores para escalar o sistema usando o kafka.
- **Tolerância à Falha:** Explicar cenários de falha e como o Kafka pode garantir resiliência.
- **Idempotência:** Definir o conceito e como o projeto evita processamento duplicado.
