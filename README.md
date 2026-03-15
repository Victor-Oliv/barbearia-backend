# 💈 Barbearia Backend - API REST

API REST desenvolvida em **Java com Spring Boot** para gerenciamento de uma barbearia.
O sistema permite administrar **clientes, barbeiros, serviços, produtos e agendamentos**, além de fornecer regras de negócio para controle de horários, disponibilidade e cancelamentos.

O objetivo do projeto é **praticar desenvolvimento backend com Spring Boot**, aplicando boas práticas, arquitetura em camadas e regras de negócio próximas de um sistema real.

---

# 🚀 Funcionalidades

### 👥 Clientes

* Cadastro de clientes
* Edição de dados
* Listagem de clientes
* Exclusão de clientes (com remoção dos agendamentos vinculados)

### 💈 Barbeiros

* Cadastro de barbeiros
* Edição de barbeiros
* Consulta de barbeiros disponíveis

### ✂️ Serviços

* Cadastro de serviços
* Edição de serviços
* Exclusão de serviços (permitida apenas se não houver agendamentos ativos)

### 📦 Produtos

* Cadastro de produtos
* Edição de produtos
* Controle de estoque

### 📅 Agendamentos

* Criação de agendamentos
* Listagem de agendamentos
* Atualização de agendamento
* Cancelamento de agendamento

Regras aplicadas:

* Cliente obrigatório
* Barbeiro obrigatório
* Serviço obrigatório
* Horário deve estar disponível
* Conflitos de agenda são bloqueados
* Cancelamento permitido apenas **com no mínimo 2 horas de antecedência**

---

# 🛠️ Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 4**
* Spring Web
* Spring Data JPA
* Hibernate
* Spring Security
* Lombok
* PostgreSQL
* Maven

---

# 🧱 Arquitetura do Projeto

O projeto segue o padrão de **arquitetura em camadas**, separando responsabilidades para facilitar manutenção e evolução do sistema.

```
controller  -> recebe requisições HTTP
service     -> regras de negócio
repository  -> acesso ao banco de dados
domain      -> entidades JPA
dto         -> objetos de transferência de dados
exception   -> tratamento global de erros
config      -> configurações do sistema
```

Estrutura principal:

```
src/main/java/com/victor/barbearia/barbearia/

config/
controller/
domain/
dto/
exception/
repository/
service/
```

---

# 📌 Regras de Negócio

O sistema implementa diversas regras comuns em sistemas de agendamento:

### Horários disponíveis

* Os horários são gerados em **intervalos de 30 minutos**
* Horários ocupados não podem ser selecionados
* Conflitos entre agendamentos são bloqueados

### Cancelamento

* Um agendamento só pode ser cancelado **com no mínimo 2 horas de antecedência**

### Serviços

* Serviços não podem ser excluídos se houver **agendamentos ativos vinculados**

### Clientes

* Ao excluir um cliente, seus agendamentos são removidos automaticamente

### Status de agendamento

Um agendamento pode possuir os seguintes status:

```
AGENDADO
FINALIZADO
CANCELADO
```

---

# 🔗 Principais Endpoints

## Autenticação

```
GET /auth/me
```

---

## Clientes

```
POST /clientes
GET /clientes
PUT /clientes/{id}
DELETE /clientes/{id}
```

---

## Barbeiros

```
POST /barbeiros
GET /barbeiros
PUT /barbeiros/{id}
```

---

## Serviços

```
POST /servicos
GET /servicos
PUT /servicos/{id}
DELETE /servicos/{id}
```

---

## Produtos

```
POST /produtos
GET /produtos
PUT /produtos/{id}
```

---

## Agendamentos

```
POST /agendamentos
GET /agendamentos
GET /agendamentos/cliente/{id}
PUT /agendamentos/{id}
DELETE /agendamentos/{id}
PATCH /agendamentos/{id}/status
```

---

# 🧪 Objetivo do Projeto

Este projeto foi criado com foco em evolução técnica e prática em:

* Desenvolvimento de **APIs REST**
* Aplicação de **regras de negócio**
* Uso de **Spring Boot e JPA**
* Organização de projetos backend
* Boas práticas de código

---

# 👨‍💻 Autor

Desenvolvido por **Victor**
Projeto voltado para **estudo e evolução em desenvolvimento backend com Java e Spring Boot**.
