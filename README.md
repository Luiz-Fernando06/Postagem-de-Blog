# Bloguinho — Sistema CRUD em Java

Projeto acadêmico desenvolvido em Java com integração ao banco de dados MySQL por meio de JDBC.  
O sistema simula uma aplicação simples de postagens de blog, permitindo cadastro de usuários, login, criação de posts, comentários e curtidas.

## 📌 Sobre o projeto

O **Bloguinho** é um sistema CRUD executado pelo terminal, criado para praticar conceitos de Programação Orientada a Objetos, organização em camadas e persistência de dados com banco relacional.

O projeto foi organizado em packages para separar responsabilidades e facilitar a manutenção do código.

## 🚀 Funcionalidades

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

## 🛠️ Tecnologias utilizadas

- Java
- MySQL
- JDBC
- MySQL Connector/J
- IntelliJ IDEA
- Git e GitHub

## 🗂️ Estrutura do projeto

```text
database/
├── bloguinho.sql
lib/
├── msql-conector
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
````

## 🧩 Organização das camadas
#### Model

Contém as entidades principais do sistema.

- Usuario: representa o usuário cadastrado.
- Post: representa uma postagem.
- Comentario: representa um comentário feito em um post.
- Curtidas: representa uma curtida feita por um usuário.
- Curtivel: interface usada para permitir curtidas em posts e comentários.

#### Repository

Responsável pela comunicação com o banco de dados usando JDBC.

Essa camada executa comandos SQL como:

- INSERT
- SELECT
- UPDATE
- DELETE

#### Service

Contém as regras de negócio do sistema.

Exemplos:

- validar campos vazios;
- impedir email duplicado;
- verificar se o usuário está logado;
- permitir edição apenas de dados próprios;
- controlar o funcionamento de curtir/descurtir.

#### Controller

Responsável pela interação com o usuário pelo terminal.

Recebe os dados digitados, chama os services e exibe as mensagens no console.

#### App

Contém a classe principal Main, responsável por iniciar o sistema e exibir os menus.

## 🗄️ Banco de dados

Banco utilizado:

- bloguinho

Principais tabelas:

- usuario
- post
- comentario
- curtidas

## ⚙️ Como executar o projeto

#### 1. Clonar o repositório
git clone https://github.com/SEU-USUARIO/NOME-DO-REPOSITORIO.git

#### 2. Abrir o projeto na IDE
Abra o projeto no IntelliJ IDEA ou em outra IDE Java de sua preferência.

#### 3. Criar o banco de dados
Execute o script SQL do projeto no MySQL.

#### 4. Configurar conexão com o banco
No arquivo: `repository/ConexaoBanco.java`

Verifique se os dados estão corretos:

- `private static final String URL = "jdbc:mysql://localhost:3306/bloguinho?useSSL=false&serverTimezone=America/Sao_Paulo";`
- `private static final String USER = "root";`
- `private static final String PASSWORD = "";`

Altere o usuário e a senha conforme a configuração do seu MySQL.

#### 5. Adicionar o driver JDBC

Adicione o MySQL Connector/J ao projeto.

Dependência necessária: `mysql-connector-j`

#### 6. Executar o sistema

Execute a classe:

- `app/Main.java`

O sistema será iniciado no terminal com o menu principal.

## 👥 Autores
- Luiz Fernando Martins dos Santos
- Christian Espíndula Mendonça

## 📚 Projeto acadêmico

Projeto desenvolvido para a disciplina de Programação Orientada a Objetos em Java, com foco em CRUD, JDBC, MySQL e organização em camadas.
