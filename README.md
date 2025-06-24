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

- **Escalabilidade:** 

Com Kafka, conseguimos escalabilidade através de:
- Partições: Cada tópico pode ter várias partições, e cada uma pode ser processada separadamente. Isso permite paralelismo.
- Múltiplos consumidores em grupo: Um grupo de consumidores pode processar diferentes partições em paralelo, aumentando a velocidade de consumo.
- Cluster de brokers: Kafka pode rodar em um cluster de máquinas, distribuindo os tópicos e partições entre os brokers.

**Exemplo:**
Se houver aumento no número de pedidos, podemos criar mais partições no tópico orders e rodar múltiplas instâncias do serviço consumidor (inventory-service) para processá-los simultaneamente, garantindo desempenho mesmo com alta carga..

- **Tolerância à Falha:** 

A tolerância à falha é a capacidade do sistema continuar funcionando mesmo que ocorra uma falha parcial, como a queda de um serviço ou máquina.

Com Kafka, isso é possível porque:
Mensagens são persistidas em disco: mesmo que o consumidor caia, a mensagem fica armazenada.

- Replicação de dados entre brokers: tópicos podem ter réplicas em múltiplos brokers (ex: replication.factor=3), então se um broker falhar, outro assume.

- Controle de offset: o consumidor só marca uma mensagem como "lida" depois de processá-la. Assim, em caso de falha, a mensagem pode ser reprocessada.


- Exemplo:
Se o inventory-service cair após começar a processar um pedido, outro consumidor pode retomar a leitura do Kafka e continuar de onde parou, evitando perda de mensagens.


- **Idempotência:** 

A idempotência é um conceito onde uma operação pode ser executada várias vezes sem alterar o resultado final.

Em sistemas distribuídos com falhas ou reenvios de mensagens, é comum o mesmo evento ser processado mais de uma vez. Sem idempotência, isso pode causar duplicações ou erros.

Como garantir:
- Verificar se a operação já foi realizada, usando um identificador único (ex: orderId).

- Registrar os eventos processados em uma tabela ou cache.

- Evitar múltiplas atualizações no banco para o mesmo pedido.


- Exemplo:
  Se o inventory-service receber o mesmo orderId duas vezes por acidente, ele deve checar se já processou esse pedido e ignorar a repetição, mantendo o sistema consistente.

