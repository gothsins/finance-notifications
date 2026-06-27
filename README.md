# finance-notifications

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen?logo=springboot)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3-orange?logo=rabbitmq)
![Docker](https://img.shields.io/badge/Docker-ready-2496ED?logo=docker)

Serviço de notificações integrado ao [finance-api](https://github.com/gothsins/finance-api) via mensageria assíncrona com RabbitMQ.

## Sobre o projeto

O finance-notifications consome eventos de transações publicados pelo finance-api numa fila RabbitMQ e processa notificações em tempo real. A comunicação é totalmente assíncrona — o finance-api responde imediatamente ao cliente e o processamento da notificação acontece em paralelo, sem bloquear a requisição original.

## Tecnologias

- Java 21
- Spring Boot 3.5
- Spring AMQP (RabbitMQ)
- Spring Web
- Lombok
- Docker / Docker Compose

## Arquitetura

```
finance-api
    ↓ publica TransactionEvent
transactions.exchange (RabbitMQ)
    ↓ routing-key: transactions.created
transactions.queue
    ↓ consome
TransactionConsumer
    ↓
NotificationService → processa e loga a notificação
```

## Configuração RabbitMQ

| Propriedade | Valor padrão |
|---|---|
| Exchange | `transactions.exchange` |
| Queue | `transactions.queue` |
| Routing Key | `transactions.created` |

## Pré-requisitos

- Java 21
- Docker
- [finance-api](https://github.com/gothsins/finance-api) rodando na porta 8080

## Como rodar

**1. Configure as variáveis de ambiente**

Crie `src/main/resources/application.properties` baseado no `application-example.properties`:

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

rabbitmq.exchange=transactions.exchange
rabbitmq.queue=transactions.queue
rabbitmq.routing-key=transactions.created
```

**2. Suba o RabbitMQ via Docker**

```bash
docker compose up -d
```

Painel de gerenciamento disponível em `http://localhost:15672` (guest/guest).

**3. Suba a aplicação**

```bash
./mvnw spring-boot:run
```

A aplicação sobe na porta `8082` e começa a ouvir a fila automaticamente.

## Como testar

Com o finance-api e o finance-notifications rodando simultaneamente, crie uma transação no finance-api (`POST /transactions`). O finance-notifications vai consumir o evento e exibir a notificação no terminal:

```
=====================================
  NOTIFICAÇÃO DE TRANSAÇÃO
=====================================
  💰 Tipo     : INCOME
  📝 Descrição : Salário
  💲 Valor     : R$ 3500.00
  📅 Data      : 2026-06-26
  🏷️  Categoria : Food
  👤 Usuário   : gui@email.com
  🕐 Processado: 2026-06-26T20:46:40
=====================================
```

## Estrutura do projeto

```
src/main/java/com/guilherme/finance_notifications/
├── config/    # RabbitMQConfig (exchange, queue, binding, converter)
├── consumer/  # TransactionConsumer (@RabbitListener)
├── dto/       # TransactionEvent
└── service/   # NotificationService
```