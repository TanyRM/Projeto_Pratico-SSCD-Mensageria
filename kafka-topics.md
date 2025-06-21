# Documentação dos Tópicos Kafka

| Nome do Tópico   | Partições | Fator de Replicação | Produtor(s)       | Consumidor(es)       | Descrição                                     | Configurações Especiais         |
|------------------|-----------|---------------------|-------------------|----------------------|-----------------------------------------------|---------------------------------|
| orders           | 1         | 1                   | Order-Service     | Inventory-Service    | Tópico para envio dos pedidos criados         | retention.ms=604800000 (7 dias) |
| inventory-events | 1         | 1                   | Inventory-Service | Notification-Service | Eventos de reserva de estoque (sucesso/falha) | cleanup.policy=delete           |

---

## Notas adicionais

- **Partições:** Número de partições define paralelismo e escalabilidade.
- **Fator de replicação:** Define quantas cópias dos dados são mantidas.
- **Configurações especiais:** Incluem políticas de retenção, compactação, etc.
- Os tópicos devem ser criados antes da execução dos serviços, usando os comandos descritos no
  arquivo [readme](./README.md).
