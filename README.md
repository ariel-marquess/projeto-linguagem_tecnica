# Sistema de Armazém

## Sobre o Projeto

O Sistema de Armazém é uma aplicação desenvolvida em Java com integração ao banco de dados MySQL, projetada para realizar o gerenciamento básico de estoque de um armazém. O sistema permite o controle de produtos por meio de operações de cadastro, consulta, atualização e exclusão, oferecendo uma interface gráfica intuitiva para facilitar sua utilização.
O projeto foi desenvolvido com o objetivo de aplicar conceitos de Programação Orientada a Objetos, integração com bancos de dados relacionais e desenvolvimento de interfaces gráficas em Java.

## Funcionalidades

O sistema possui as seguintes funcionalidades:
- Autenticação de usuários por meio de login;
- Cadastro de novos produtos;
- Adição de produtos ao estoque;
- Remoção de produtos do estoque;
- Consulta e verificação dos produtos cadastrados;
- Atualização das informações dos produtos;
- Exclusão de produtos do sistema;
- Validação de dados inseridos pelo usuário;
- Exibição de mensagens de erro para entradas inválidas.

## Informações Gerenciadas

Para cada produto, o sistema armazena as seguintes informações:
- Código do produto;
- Nome do produto;
- Quantidade em estoque;
- Valor unitário;
- Valor total em estoque.

## Tecnologias Utilizadas

- Java;
- MySQL;
- JDBC (Java Database Connectivity);
- Interface gráfica desenvolvida em Java.

## Estrutura do Sistema

O sistema é composto pelos seguintes módulos:
- Tela de Login;
- Menu Principal;
- Cadastro de Produtos;
- Adição de Estoque;
- Remoção de Estoque;
- Verificação de Estoque;
- Atualização de Produtos;
- Exclusão de Produtos;
- Conexão com Banco de Dados.

## Configuração do Banco de Dados

1. Instale o MySQL Server;
2. Crie um banco de dados para a aplicação;
3. Execute o script SQL disponibilizado no projeto para criação das tabelas necessárias;
4. Configure as credenciais de acesso ao banco de dados no arquivo responsável pela conexão.

Exemplo:

```java
String url = "jdbc:mysql://localhost:3306/nome_do_banco";
String usuario = "seu_usuario";
String senha = "sua_senha";
```

## Como Executar o Projeto

### Pré-requisitos

- Java JDK 8 ou superior;
- MySQL Server;
- Driver JDBC para MySQL;
- Ambiente de desenvolvimento Java, como Eclipse, IntelliJ IDEA ou NetBeans.

### Instalação e Execução

1. Clone o repositório:
```bash
git clone https://github.com/ariel-marquess/projeto-linguagem_tecnica.git
```
2. Importe o projeto em sua IDE de preferência;
3. Configure a conexão com o banco de dados MySQL;
4. Execute a classe principal do projeto.

## Demonstração das Funcionalidades

O sistema disponibiliza interfaces para:
- Login e autenticação de usuários;
- Cadastro de novos produtos;
- Adição e remoção de estoque;
- Consulta detalhada dos produtos cadastrados;
- Atualização e exclusão de registros;
- Tratamento e exibição de mensagens de erro.

## Objetivo do Projeto

Desenvolver uma aplicação para gerenciamento de estoque utilizando Java e MySQL, aplicando conhecimentos relacionados a:
- Programação Orientada a Objetos;
- Manipulação de bancos de dados relacionais;
- Desenvolvimento de interfaces gráficas;
- Validação de dados;
- Operações CRUD (Create, Read, Update e Delete).

## Protótipo

O protótipo da interface do Sistema de Armazém foi desenvolvido utilizando o Figma e pode ser acessado pelo link abaixo:
- Projeto no Figma: https://www.figma.com/design/D4wmkPsLRWY5WEfd3v5kv3/Armazem?node-id=0-1&t=Npr7qKqwYQ9u5Sb1-1

## Integrantes

- Willian
- Ariel
- Heitor
- Ricardo
- Alana

## Licença

Este projeto foi desenvolvido para fins acadêmicos e educacionais.
