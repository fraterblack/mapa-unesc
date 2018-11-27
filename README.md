# Projeto Interdisciplinar da Computação
##### Alunos: Edvaldo da Rosa, Bruno Firmizin Crema, Gabriel Fernandes Pereira e Enzo Abadi José Réus

#### Apresentação: 28/11/2018

## Tecnologias utilizadas
* Java 1.8
* MySql 8.0
* Maven 3.6.0
* Spark 2.7.2 - Micro Framework
* ORM Lite 5.1 - Object Relational Mapping
* Jackson 2.9.6 - JSON Parser/Writer
* HTML 5
* CSS 3
* Javascript

## Requisitos
* Java 1.8
* Maven 3.6.0
* MySQL Database

## Configuração conexão com banco de dados
Usuário, senha e nome do banco de dados pode ser configurado na classse _br.com.unesc.shared.DataBaseManager_

## A instalação das tabelas pode ser feita de duas maneiras:
* Executar a classe _br.com.unesc.Migrations_. Todas as tabelas serão criadas automaticamente, bem como as rotas serão importadas.
* Importar o arquivo SQL disponível na pasta unesc/data deste projeto

## Rodar o projeto
Basta executar a classe classe _br.com.unesc.Application_, em seguida acessar a URL _http://localhost:4567_ no seu navegador
