# Bloguinho — Sistema CRUD em Java

Projeto acadêmico desenvolvido em Java com integração ao banco de dados MySQL por meio de JDBC.  
O sistema simula uma aplicação simples de postagens de blog, permitindo cadastro de usuários, login, criação de posts, comentários e curtidas.

##  Sobre o projeto

O **Bloguinho** é um sistema CRUD executado pelo terminal, criado para praticar conceitos de Programação Orientada a Objetos, organização em camadas e persistência de dados com banco relacional.

O projeto foi organizado em packages para separar responsabilidades e facilitar a manutenção do código.

## Funcionalidades

- Cadastro de usuários
- Login e logout
- Edição de dados do usuário
- Criação de posts
- Edição de posts
- Remoção de posts
- Listagem de posts
- Busca de posts por ID ou título
- Criação de comentários em posts
- Edição de comentários
- Remoção de comentários
- Listagem de comentários de um post
- Curtir e descurtir posts
- Curtir e descurtir comentários

## Tecnologias utilizadas

- Java
- MySQL
- JDBC
- MySQL Connector/J
- IntelliJ IDEA
- Git e GitHub

## Estrutura do projeto

```text
src/
├── app/
│   ├── Main.java
│   └── Utilitarios.java
│
├── controller/
│   ├── UsuarioController.java
│   ├── PostController.java
│   ├── ComentarioController.java
│   └── CurtidasController.java
│
├── model/
│   ├── Usuario.java
│   ├── Post.java
│   ├── Comentario.java
│   ├── Curtidas.java
│   └── Curtivel.java
│
├── repository/
│   ├── ConexaoBanco.java
│   ├── UsuarioRepository.java
│   ├── PostRepository.java
│   ├── ComentarioRepository.java
│   └── CurtidasRepository.java
│
└── service/
    ├── UsuarioService.java
    ├── PostService.java
    ├── ComentarioService.java
    └── CurtidasService.java
