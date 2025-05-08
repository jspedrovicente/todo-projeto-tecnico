# ✅ Painel To-Do - Projeto Técnico Fullstack

Este projeto é um painel de tarefas (_To-Do_) com funcionalidades completas para **criar**, **editar**, **concluir** e **filtrar** tarefas.  
A aplicação foi desenvolvida com **Angular** no frontend e **Spring Boot** no backend, utilizando o **banco H2 em memória**, permitindo que qualquer pessoa possa clonar e testar rapidamente sem precisar configurar banco de dados.

---

## 🚀 Como Rodar o Projeto

### 🧩 Pré-requisitos

- Node.js (v18 ou superior)
- Java 17 ou superior
- Maven
- Angular CLI (`npm install -g @angular/cli`)

---

### ▶️ 1. Rodando o Backend (Spring Boot + H2)

```bash
cd backend
./mvnw spring-boot:run

O backend estará disponível em: http://localhost:8080
Console do H2: http://localhost:8080/h2-console

Credenciais para o H2:
JDBC URL: jdbc:h2:mem:testdb
Usuário: sa
Senha: (deixe em branco)


### ▶️ 2. Rodando o Frontend (Angular)
cd todo-projet-tecnico-front
npm install
ng serve

O frontend estará disponível em: http://localhost:4200


📦 Funcionalidades
✅ Criar nova tarefa

✅ Editar tarefa existente

✅ Marcar como concluída ou reabrir

✅ Filtrar por nome, prioridade e situação

✅ Validação de formulário

✅ Notificações visuais

✅ Layout moderno e responsivo

🛠️ Tecnologias Utilizadas
Frontend
Angular

PrimeNG

TailwindCSS

Notyf (notificações)

Backend
Java 17

Spring Boot

Spring Web

Spring Data JPA

Banco H2 (em memória)

### 📁 Estrutura de Pastas
todo-projeto-tecnico/
├── todo-projeto-tecnico-backend/   # Projeto Spring Boot (API REST)
└── todo-projeto-tecnico-front/  # Projeto Angular


