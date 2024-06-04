# SistemaGerenciadorDeVendas
Um Sistema de Gerenciamento de Vendas feito utilizando Java & Java Swing como design.

Oque fazer para utilizar ?

Use a biblioteca 'mysql-connector-j-8.4.0'

Tabelas do banco de dados

Tabela clientes CREATE TABLE Clientes ( id INT PRIMARY KEY AUTO_INCREMENT, nome VARCHAR(100) );

CREATE TABLE Produtos ( id INT PRIMARY KEY AUTO_INCREMENT, preco DECIMAL(10, 2), descricao TEXT );

CREATE TABLE Pedidos ( id INT PRIMARY KEY AUTO_INCREMENT, dtCadastro DATE, ClienteId INT, FOREIGN KEY (ClienteId) REFERENCES Clientes(id) );

CREATE TABLE Itens ( Id INT PRIMARY KEY AUTO_INCREMENT, PedidoId INT, ProdutoId INT, Quantidade INT, Preco DECIMAL(10, 2), FOREIGN KEY (PedidoId) REFERENCES Pedidos(id), FOREIGN KEY (ProdutoId) REFERENCES Produtos(id) );
