# 💈 Sistema de Barbearia - API REST

Projeto backend desenvolvido em **Java com Spring Boot**, simulando o gerenciamento completo de uma barbearia, incluindo **clientes, barbeiros, serviços e agendamentos**.
O objetivo principal é **praticar conceitos avançados de backend**, boas práticas e regras de negócio do mundo real.

---

## 🚀 Funcionalidades

* Cadastro, edição, listagem e remoção de **Clientes**
* Cadastro e gerenciamento de **Barbeiros**
* Cadastro de **Serviços** (ex: corte, barba, combo, etc.)
* **Agendamento de horários**, respeitando regras de negócio:
  * Cliente obrigatório
  * Barbeiro obrigatório
  * Serviço obrigatório
  * Data e horário válidos
* Cálculo de valor total do agendamento
* Validações de dados
* Arquitetura em camadas (Controller, Service, Repository)

---

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot**
* Spring Web
* Spring Data JPA
* Hibernate
* Lombok
* Banco de dados PostgreSQL
* Maven

---

## 🧱 Arquitetura do Projeto

O projeto segue o padrão de **arquitetura em camadas**:

```
controller  -> recebe as requisições HTTP
service     -> regras de negócio
repository  -> acesso ao banco de dados
domain      -> entidades do sistema
```

Essa separação facilita manutenção, testes e escalabilidade.

---

## 📌 Regras de Negócio Implementadas

* Um agendamento **não pode ser criado** sem:
  * Cliente válido
  * Barbeiro válido
  * Serviço válido
* O sistema valida IDs antes de persistir os dados
* Datas e horários são tratados com `LocalDate` e `LocalTime`
* Valores monetários utilizam `BigDecimal`

---

## 🔗 Exemplos de Endpoints

### Criar agendamento

```http
POST /agendamentos
```

### Listar agendamentos

```http
GET /agendamentos
```

### Criar cliente

```http
POST /clientes
```

### Criar barbeiro

```http
POST /barbeiros
```

*(Os endpoints seguem padrão RESTful)*

---

## 🧪 Objetivo do Projeto

Este projeto foi desenvolvido com foco em:

* Consolidar conhecimentos em **Spring Boot**
* Praticar **validações e regras de negócio**
* Trabalhar com **JPA e relacionamentos**
* Simular um sistema próximo da realidade
* Evoluir boas práticas de código e organização

---

## 👨‍💻 Autor
Desenvolvido por **Victor**
Projeto de estudo e evolução.
