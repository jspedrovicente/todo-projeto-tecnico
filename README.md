# ✅ Painel To-Do - Projeto Técnico Fullstack

Este projeto é um painel de tarefas (_To-Do_) com funcionalidades completas para **criar**, **editar**, **concluir** e **filtrar** tarefas.  
A aplicação foi desenvolvida com **Angular** no frontend e **Spring Boot** no backend, utilizando o **banco H2 em memória**.

---

## 🚀 Como Rodar o Projeto

### 🧩 Pré-requisitos

- Java 17 ou superior
- Maven
- Angular CLI (`npm install -g @angular/cli`)

---

### ▶️ 1. Rodando o Backend (Spring Boot + H2)

No Terminal (RECOMENDADO):
```bash
cd todo-projeto-tecnico-backend
cd todo-backendT
./mvnw spring-boot:run
```
No IDE:
```bash
Abrir o IDE
Selecionar a pasta todo-projeto-tecnico-backend
Carregar o Maven
ir para: todo-backend/src/main/java/com.example.todo_backend/TodoBackendApplication e rodar o Main
```

O banco em memoria estará disponível em: http://localhost:8080/h2-console

Credenciais para o H2:
JDBC URL: jdbc:h2:mem:testdb
Usuário: sa
Senha: (deixe em branco)

---
### ▶️ 2. Rodando o Frontend (Angular)

```bash
cd todo-projeto-tecnico-front
npm install
ng serve
```

O frontend estará disponível em: http://localhost:4200

---

### 📦 Funcionalidades

✅ Criar nova tarefa

✅ Editar tarefa existente

✅ Marcar como concluída ou reabrir

✅ Filtrar por nome, prioridade e situação

✅ Validação de formulário

✅ Notificações visuais

✅ Layout moderno e responsivo

---

### 🛠️ Tecnologias Utilizadas

### Frontend:

Angular

PrimeNG 

TailwindCSS

Notyf (notificações)



### Backend:

Java 17

Spring Boot

Spring Web

Spring Data JPA

Banco H2 (em memória)

---


### 📘 Documentação da API (Swagger)

A documentação da API está disponível via Swagger:

🔗 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
