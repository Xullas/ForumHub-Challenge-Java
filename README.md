# Challenge Forum Hub API 🗣️

![Status do Projeto](https://img.shields.io/badge/Status-Concluído-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13-blue)
![Docker](https://img.shields.io/badge/Docker-gray?logo=docker)
![Security](https://img.shields.io/badge/Security-JWT-critical)

## 📖 Índice

- [1. Descrição do Projeto](#-1-descrição-do-projeto)
- [2. Funcionalidades](#-2-funcionalidades)
- [3. Tecnologias Utilizadas](#-3-tecnologias-utilizadas)
- [4. Decisões de Arquitetura e Design](#-4-decisões-de-arquitetura-e-design)
    - [4.1. Uso de JDBC em vez de Spring Data JPA](#-41-uso-de-jdbc-em-vez-de-spring-data-jpa)
    - [4.2. Ambiente de Desenvolvimento com Docker](#-42-ambiente-de-desenvolvimento-com-docker)
    - [4.3. Segurança com JWT e Autorização por Autoria](#-43-segurança-com-jwt-e-autorização-por-autoria)
- [5. Acesso ao Projeto](#-5-acesso-ao-projeto)
- [6. Abrindo e Executando o Projeto](#%EF%B8%8F-6-abrindo-e-executando-o-projeto)
- [7. Estrutura do Projeto](#-7-estrutura-do-projeto)
- [8. Melhorias Futuras](#-8-melhorias-futuras)
- [9. Contribuição](#-9-Contribuição)

## 📌 1. Descrição do Projeto

O **Forum Hub** é uma API REST desenvolvida como parte do segundo Challenge proposto na trilha **Java e Spring Framework G8** do **ONE - Oracle Next Education** em parceria com a **Alura**. A API permite a criação e gerenciamento de tópicos de discussão, com um sistema de autenticação robusto para garantir que as operações sejam seguras.

O principal objetivo do projeto foi construir uma API segura e funcional, aplicando conceitos avançados de Spring Boot e tomando decisões de arquitetura conscientes para aprofundar o aprendizado em tecnologias fundamentais do ecossistema Java, como a comunicação direta com o banco de dados via JDBC e a conteinerização do ambiente com Docker.

## 🎯 2. Funcionalidades

A API REST oferece os seguintes endpoints para interação:

* ✅ **`[POST] /login`**: Autentica um usuário com base em `email` e `senha`, retornando um token JWT para ser usado nas requisições subsequentes.
* ✅ **`[POST] /usuarios`**: Permite o cadastro de um novo usuário no sistema.
* ✅ **`[GET] /topicos`**: Lista todos os tópicos de forma paginada, otimizando a performance e o consumo de dados.
* ✅ **`[POST] /topicos`**: Cria um novo tópico. Requer um token de autenticação válido.
* ✅ **`[GET] /topicos/{id}`**: Exibe os detalhes de um tópico específico. Requer autenticação.
* ✅ **`[PUT] /topicos/{id}`**: Atualiza os dados de um tópico. A operação só é permitida se o usuário autenticado for o autor original do tópico.
* ✅ **`[DELETE] /topicos/{id}`**: Exclui um tópico do banco de dados. Assim como na atualização, só o autor do tópico pode realizar esta ação.

## 🚀 3. Tecnologias Utilizadas

- **Linguagem:** Java 17
- **Framework:** Spring Boot 3
- **IDE:** IntelliJ IDEA
- **Persistência:** Spring JDBC
- **Banco de Dados:** PostgreSQL
- **Segurança:** Spring Security
- **Ambiente:** Docker e Docker Compose
- **Build Tool:** Maven

## 💡 4. Decisões de Arquitetura e Design

Este projeto foi uma oportunidade para explorar e consolidar conhecimentos em tecnologias e práticas de mercado. Por isso, algumas decisões foram tomadas para maximizar o aprendizado.

### 💾 4.1. Uso de JDBC em vez de Spring Data JPA

Embora Spring Data JPA seja a abordagem mais comum para persistência, optei conscientemente por utilizar **JDBC** puro. A motivação para essa escolha foi:

- **Aprofundar o conhecimento em SQL:** Escrever as queries manualmente permitiu praticar e reforçar a linguagem SQL, além de ter um controle total sobre a interação com o banco de dados.
- **Controle Total sobre a Persistência:** O uso de JDBC oferece um controle granular sobre cada operação, evitando a "mágica" das abstrações e forçando um entendimento claro do fluxo de dados entre a aplicação e o banco.
- **Treinamento Intencional:** Foi uma escolha deliberada para treinar a linguagem e os fundamentos de conectividade de banco de dados em Java, um conhecimento essencial para qualquer desenvolvedor back-end.

### 🐳 4.2. Ambiente de Desenvolvimento com Docker

Diferente da recomendação de instalar o banco de dados diretamente na máquina local, escolhi utilizar **Docker e Docker Compose** para gerenciar o ambiente de banco de dados. Os benefícios dessa abordagem são:

- **Portabilidade e Consistência:** Qualquer desenvolvedor pode clonar o projeto e subir o ambiente com um único comando (`docker-compose up`), garantindo que todos usem a mesma versão e configuração do banco, eliminando o clássico problema de "funciona na minha máquina".
- **Isolamento:** O banco de dados roda em um contêiner isolado, sem interferir com outros serviços ou instalações na máquina host.
- **Facilidade de Setup:** A configuração do banco, incluindo usuário, senha e banco de dados inicial, é definida no arquivo `docker-compose.yml`, tornando o processo de inicialização rápido e automatizado.

### 🔐 4.3. Segurança com JWT e Autorização por Autoria

Para a camada de segurança, a decisão foi implementar um fluxo de autenticação stateless com JSON Web Tokens (JWT).

- **Autenticação Stateless:** O uso de JWT elimina a necessidade de armazenar o estado da sessão no servidor, tornando a API mais escalável e robusta.
- **Autorização por Autoria:** Em vez de confiar em um `id` de autor enviado no corpo da requisição (o que seria uma falha de segurança), a lógica de negócio extrai a identidade do usuário diretamente do token JWT. Isso garante que as operações de `PUT` e `DELETE` em um tópico só possam ser executadas pelo seu verdadeiro autor, tornando a regra de negócio segura e centralizada na camada de segurança.

## 🔑 5. Acesso ao Projeto

**Pré-requisitos:**

-   Java Development Kit (JDK) 17 ou superior
-   Maven 3.8 ou superior
-   Docker e Docker Compose

## ⚙️ 6. Abrindo e Executando o Projeto

Siga os passos abaixo para executar a aplicação:

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/Xullas/ForumHub-Challenge-Java.git
    ```

2.  **Navegue até o diretório do projeto:**
    ```bash
    cd ForumHub-Challenge-Java
    ```

3.  **Inicie o banco de dados com Docker Compose:**
    Este comando irá baixar a imagem do PostgreSQL (se não existir localmente) e iniciar o contêiner em segundo plano (`-d`).
    ```bash
    docker-compose up -d
    ```

4.  **Execute a aplicação Spring Boot:**
    Utilize o Maven Wrapper para compilar e executar o projeto. O Spring Boot irá se conectar automaticamente ao banco de dados rodando no Docker.
    ```bash
    ./mvnw spring-boot:run
    ```

Após a inicialização, a API estará disponível em `http://localhost:8080`.

## 📁 7. Estrutura do Projeto
A estrutura de pacotes do projeto foi organizada para separar as responsabilidades de forma clara:

* `src/main/java/com/forumhub/`
    * `ForumhubApplication.java`: Classe principal que inicia a aplicação Spring Boot.
    * **config**: Classes de configuração do Spring, como a de segurança (`SecurityConfigurations`).
    * **controller**: Controladores REST que expõem os endpoints da API (`AutenticacaoController`, `TopicoController`, etc.).
    * **domain**: Contém as classes de modelo (`Topico`, `Usuario`) e os DTOs (Data Transfer Objects) para entrada e saída de dados.
    * **exception**: Handlers para tratamento de exceções customizadas da aplicação.
    * **repository**: Camada de acesso a dados (Data Access Layer), com as implementações de JDBC.
    * **service**: Camada de regras de negócio, que orquestra as operações entre os controladores e os repositórios.
* `src/main/resources/`
    * `application.properties`: Arquivo principal de configuração do Spring.
    * **db/migration**: Contém os scripts SQL para versionamento do banco de dados com o Flyway (se utilizado).

## 🔮 8. Melhorias Futuras
O projeto foi concluído com o escopo definido. As seguintes funcionalidades foram intencionalmente deixadas para futuras implementações:

* `[GET] /usuarios`: Listagem de todos os usuários cadastrados.
* `[PUT] /usuarios/{id}`: Atualização dos dados de um usuário.
* `[DELETE] /usuarios/{id}`: Exclusão de um usuário.
* Implementar uma entidade de `Respostas` para permitir discussões dentro dos tópicos.
* Adicionar testes unitários e de integração para garantir a qualidade e a manutenibilidade do código.
* 
## 🏆 9. Contribuição
Se quiser sugerir melhorias ou relatar bugs, fique à vontade para abrir uma **issue** ou fazer um **pull request**.<br>
Toda contribuição é bem-vinda! 🚀

---
Feito com ❤️ por [Xullas](https://github.com/Xullas) 😊
