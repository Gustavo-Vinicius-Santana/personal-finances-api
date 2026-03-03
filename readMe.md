# ☕ Personal Finance Manager - Back-end

API REST desenvolvida em **Java com Spring Boot** para gerenciamento de finanças pessoais, responsável pelo controle de autenticação de usuários, registro de receitas e despesas, e cálculo do saldo total com persistência em banco de dados **PostgreSQL**.

A aplicação segue arquitetura em camadas, boas práticas de organização de código e princípios RESTful.

---

## 🚀 Tecnologias Utilizadas

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Docker Compose](https://img.shields.io/badge/Docker_Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://docs.docker.com/compose/)

---

## 📌 Funcionalidades

- ✅ Cadastro de usuários  
- ✅ Autenticação com geração de token  
- ✅ Criação de receitas (entradas)  
- ✅ Criação de despesas (saídas)  
- ✅ Listagem de movimentações financeiras por usuário  
- ✅ Cálculo automático do saldo total  
- ✅ Proteção de endpoints privados  
- ✅ Relacionamento entre usuário e movimentações  

---

## 🏗️ Arquitetura da Aplicação

O projeto segue o padrão em camadas:

- **Controller** → Responsável pelos endpoints REST  
- **Service** → Regras de negócio  
- **Repository** → Comunicação com o banco de dados  
- **Model/Entity** → Mapeamento das entidades  
- **DTO** → Transferência de dados entre camadas  

A API foi estruturada seguindo os princípios REST, utilizando métodos HTTP adequados (`GET`, `POST`, `PUT`, `DELETE`) e retornos padronizados.

---


## ▶️ Como rodar a aplicação

### 0. Configure as variaveis de ambiente
Preencha as variaveis de ambiente e deixe o arquivo como .env

### 1. Rodando com **Docker Compose**
O jeito mais simples: subir API e banco juntos em containers.

```bash
# Clonar o repositório
git clone https://github.com/Gustavo-Vinicius-Santana/personal-finances-api
cd personal-finance-api

# Subir os containers
docker compose up -d --build
```
➡️ A API ficará disponível em:
```arduino
http://localhost:8080
```

---

### 2. Rodando localmente pelo terminal
Caso prefira rodar a API fora do Docker, você pode usar Maven e apontar para um PostgreSQL do docker
```bash
# Clonar o repositório
git clone https://github.com/Gustavo-Vinicius-Santana/personal-finances-api
cd personal-finance-api

# rodar banco de dados docker
docker compose up -d postgres

# Rodar a aplicação
./mvnw spring-boot:run
```
➡️ A API ficará disponível em:
```arduino
http://localhost:8080
```

---
### 3. Rodando pelo IntelliJ IDEA
Você também pode rodar a aplicação diretamente pelo **IntelliJ IDEA**, utilizando o suporte a variáveis de ambiente.

```bash
# Clonar o repositório
git clone https://github.com/Gustavo-Vinicius-Santana/personal-finances-api
cd personal-finance-api

# Rodar apenas o banco de dados no Docker
docker compose up -d personal-finance-db
````
