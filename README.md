# CampusRide — API de Caronas Solidárias

RMs:

Kayque Duarte - 567980
Gabriel Correa - 567903
Eric Maciel - 567398

API REST para a plataforma de caronas solidárias entre alunos de uma
universidade. Um motorista publica uma carona; outros alunos consultam e
reservam vaga nela.

Projeto do checkpoint **Java Advanced — Projeto Diamante**.

## Stack

- Java 17
- Spring Boot 3.3 (Web, Data JPA, Validation)
- Banco H2 em memória (não precisa instalar nada para rodar)
- Maven

## Como executar

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.
Console do H2 (opcional, para inspecionar o banco): `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:campusride`, usuário `sa`, senha em branco).

Para rodar os testes automatizados (se houver):

```bash
mvn test
```

Para gerar o `.jar` executável:

```bash
mvn clean package
java -jar target/campusride-1.0.0.jar
```

## Estrutura do projeto

```
com.campusride
 ├── domain          → entidades (Carona, Reserva) e enums de domínio
 ├── repository      → Spring Data JPA repositories
 ├── dto             → objetos de entrada/saída da API (não expõem a entidade)
 ├── controller      → camada REST (não contém lógica de negócio)
 ├── service         → regras de negócio
 ├── validation      → anotação de validação customizada
 └── exception       → tratamento de erros centralizado
```

## Domínio

**Carona**: motorista, origem, destino, horário de partida (deve ser
futuro), tipo de veículo, vagas totais (mínimo 1). Situações:
`ABERTA`, `LOTADA`, `EM_ANDAMENTO`, `CONCLUIDA`, `CANCELADA`.

**Reserva**: carona à qual pertence, dados do passageiro, momento em que foi
feita (automático). Situações: `CONFIRMADA`, `CANCELADA`.

### Validação customizada

`@VagasCompativelComVeiculo` (anotação a nível de classe, aplicada em
`CaronaRequestDTO`) garante que o número de vagas oferecidas seja compatível
com o tipo de veículo escolhido:

| Veículo | Capacidade máxima de vagas |
|---------|-----------------------------|
| MOTO    | 1                            |
| CARRO   | 4                            |
| SUV     | 6                            |
| VAN     | 10                           |

### Regras de negócio impedidas pela API

- Reservar vaga em carona sem vagas disponíveis
- Reservar vaga em carona cancelada, já concluída ou em andamento
- Cancelar uma reserva ou carona já concluída

Toda violação (validação ou regra de negócio) retorna um JSON de erro
padronizado, sem stack trace, no formato:

```json
{
  "timestamp": "2026-09-17T10:00:00",
  "status": 422,
  "error": "Regra de negocio violada",
  "message": "Carona sem vagas disponiveis",
  "path": "/caronas/1/reservas"
}
```

## Endpoints e exemplos de chamada

### 1. Publicar uma carona

`POST /caronas`

```bash
curl -X POST http://localhost:8080/caronas \
  -H "Content-Type: application/json" \
  -d '{
        "motoristaNome": "Ana Souza",
        "origem": "Campus Central",
        "destino": "Terminal Rodoviário",
        "horarioPartida": "2026-12-20T18:30:00",
        "tipoVeiculo": "CARRO",
        "vagasTotais": 3
      }'
```

Resposta `201 Created`:

```json
{
  "id": 1,
  "motoristaNome": "Ana Souza",
  "origem": "Campus Central",
  "destino": "Terminal Rodoviário",
  "horarioPartida": "2026-12-20T18:30:00",
  "tipoVeiculo": "CARRO",
  "vagasTotais": 3,
  "vagasDisponiveis": 3,
  "situacao": "ABERTA"
}
```

### 2. Listar caronas disponíveis

`GET /caronas` (por padrão retorna apenas situação `ABERTA`; use
`?todas=true` para ver todas as situações)

```bash
curl http://localhost:8080/caronas
curl http://localhost:8080/caronas?todas=true
```

### 3. Consultar o detalhe de uma carona (com suas reservas)

`GET /caronas/{id}`

```bash
curl http://localhost:8080/caronas/1
```

### 4. Reservar uma vaga em uma carona

`POST /caronas/{id}/reservas`

```bash
curl -X POST http://localhost:8080/caronas/1/reservas \
  -H "Content-Type: application/json" \
  -d '{ "passageiroNome": "Bruno Lima" }'
```

Resposta `201 Created`:

```json
{
  "id": 1,
  "caronaId": 1,
  "passageiroNome": "Bruno Lima",
  "momentoReserva": "2026-09-17T10:05:00",
  "situacao": "CONFIRMADA"
}
```

### 5. Consultar uma reserva

`GET /reservas/{id}`

```bash
curl http://localhost:8080/reservas/1
```

### 6. Cancelar uma reserva

`DELETE /reservas/{id}`

```bash
curl -X DELETE http://localhost:8080/reservas/1
```

Resposta `204 No Content`.

### 7. Cancelar uma carona (cancela também as reservas associadas)

`DELETE /caronas/{id}`

```bash
curl -X DELETE http://localhost:8080/caronas/1
```

Resposta `204 No Content`.

## Checkpoints

Ver histórico de commits do repositório — cada checkpoint (CP1 a CP5) tem
um commit próprio com uma justificativa curta da decisão tomada naquele
trecho.
