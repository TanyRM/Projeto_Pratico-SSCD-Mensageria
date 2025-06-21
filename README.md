# Projeto: Sistema Distribuído com Kafka e Java

## Descrição

Sistema distribuído para plataforma de e-commerce, composto por três serviços integrados via Apache Kafka:
Order-Service, Inventory-Service e Notification-Service.

## Integrantes

- Raquel Dias (Order-Service + Frontend)
- Taniele Rocha (Inventory-Service + Notification-Service)

## Arquitetura

- **Order-Service:** Produtor que expõe uma REST API e publica pedidos no tópico `orders`.
- **Inventory-Service:** Consumidor do tópico `orders`, reserva estoque e publica resultado no
  tópico `inventory-events`.
- **Notification-Service:** Consumidor do tópico `inventory-events`, simula envio de notificações.

### Diagrama de Fluxo

<!-- (se possível) Adicionar diagrama ilustrando o fluxo entre os serviços e os tópicos Kafka -->

## Tópicos Kafka

| Tópico           | Partições | Produtor          | Consumidor           | Descrição                       |
|------------------|-----------|-------------------|----------------------|---------------------------------|
| orders           | 1         | Order-Service     | Inventory-Service    | Pedidos confirmados             |
| inventory-events | 1         | Inventory-Service | Notification-Service | Resultado da reserva de estoque |

mais informações em [Tópicos Kafka](./kafka-topics.md)

## Como Executar

Siga os passos na ordem correta para garantir uma inicialização sem erros.

### 1. Pré-requisitos

- **Java (JDK 17 ou superior)**
- **Maven**
- **Apache Kafka:** [Download oficial](https://kafka.apache.org/downloads).
- **Servidor de Banco de Dados:** MariaDB ou MySQL.
- **Ferramenta de Banco de Dados:** DBeaver, MySQL Workbench, etc.
- **Ferramenta de API (Opcional):** Postman, Insomnia, ou a linha de comando.

### 2. Configuração do Ambiente

1. **Inicie o Zookeeper e o Kafka:** Navegue até a pasta de instalação do Kafka e execute:
   ```bash
   .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
   
   # Em outro terminal
   .\bin\windows\kafka-server-start.bat .\config\server.properties
   ```

2. **Crie os Tópicos Kafka:**
   ```bash
   .\bin\windows\kafka-topics.bat --create --topic orders --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
   .\bin\windows\kafka-topics.bat --create --topic inventory-events --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
   ```

3. **Clone o Repositório:**
   ```bash
   git clone [https://github.com/TanyRM/Projeto_Pratico-SSCD-Mensageria.git](https://github.com/TanyRM/Projeto_Pratico-SSCD-Mensageria.git)
   cd Projeto_Pratico-SSCD-Mensageria
   ```

4. **Crie e Popule os Bancos de Dados**:
    - Use sua ferramenta de banco de dados para executar os scripts SQL que estão na pasta `/scripts` do projeto.
        1. Execute `init-order-db.sql` e `init-inventory-db.sql` para criar as tabelas.
        2. Execute `populate-inventory.sql` e `populate-orders.sql` para adicionar dados.

5. **Configure as Senhas do Banco:**
    - Em cada serviço (`order-service` e `inventory-service`), abra o
      arquivo `src/main/resources/application.properties`.
    - Encontre a linha `spring.datasource.password=sua-senha-aqui` e substitua pela sua senha local do MariaDB/MySQL.

### 3. Execução dos Serviços

- Compile e execute cada serviço Spring Boot.

## Exemplos de Uso

- Realize um pedido através da interface
- Verifique os logs dos serviços para acompanhar o fluxo das mensagens.

## Testando o Backend (via API)

Para testar o fluxo sem o frontend.

1. **Abra um terminal:**
2. **Execute o comando abaixo** para criar um novo pedido:
   ```powershell
   Invoke-RestMethod -Uri http://localhost:8080/orders -Method Post -ContentType 'application/json' -Body '{"customer":{"nome":"Cliente Teste API","email":"teste.api@email.com","telefone":"123456789"},"items":[{"produtoId":"prod-001","quantidade":1,"precoUnidade":5500.00}]}'
   ```
3. **Observe os consoles** de cada serviço para ver as mensagens sendo processadas.
4. **Consulte o status final** do pedido (substitua pelo ID retornado no passo anterior):
   ```powershell
   Invoke-RestMethod -Uri http://localhost:8080/orders/seu-order-id-aqui
   ```
   O status deverá ter mudado de `PENDENTE` para `APROVADO`.

## Requisitos Funcionais

- RF-1: Criação dos tópicos orders e inventory-events.
- RF-2: API REST para pedidos.
- RF-3: Processamento de pedidos e reserva de estoque.
- RF-4: Registro de notificações no console.

## Requisitos Não-Funcionais

- **Escalabilidade:** Descrever como usar aumentar partições e consumidores para escalar o sistema usando o kafka.
- **Tolerância à Falha:** Explicar cenários de falha e como o Kafka pode garantir resiliência.
- **Idempotência:** Definir o conceito e como o projeto evita processamento duplicado.
