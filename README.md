# README.md

API REST desenvolvida em Java com Spring Boot para gerenciamento de usuários, incluindo autenticação JWT, validação de dados e tratamento de erros padronizado com ProblemDetail (RFC 7807).

## 🚀 Tecnologias

- Java 21
- Spring Boot
- MySQL
- Docker / Docker Compose
- JWT
- Swagger (OpenAPI)

## 📌 Funcionalidades

- Autenticação via token
- CRUD de usuários
- Busca por Id, nome ou paginada
- Alteração de senha
- Validação de dados
- Tratamento de erros

## ▶️ Como executar o projeto

### Passos

```
git clone https://github.com/gideao025/FIAP_GP18/tree/main
cd FIAP_GP18
docker compose up
```

### Acesso

- API: [http://localhost:8080](http://localhost:8080/)
- Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Observações

- A aplicação já está publicada no Docker Hub:rafatespindola/restaurant-app
- Não é necessário realizar build local

## 📖 Documentação

A documentação Swagger pode ser acessada em:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui/index.html)

## 🔐 Endpoints principais

```
POST /auth/login -> Login
GET /api/v1/users/{id} -> Obter usuário por id
GET /api/v1/users/name?name={name} -> Listar por nome
GET /api/v1/users?page={page}&size={size} -> Listar paginada de todos usuários
DELETE /api/v1/users/{id} -> Deletar usuário por id
PATCH /api/v1/users/password/{id} -> Atualizar senha
POST /api/v1/users -> Criar usuário
PUT /api/v1/users/{id} -> Atualizar usuário
```

## 📌 Observações

- O sistema possui validação de e-mail único
- Implementado padrão ProblemDetail para erros