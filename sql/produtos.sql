-- =====================================================================
-- Banco "loja": Categorias e Produtos (relacao 1:N)
-- Projeto: java_swing_sql/Produtos
-- Como rodar no MySQL Workbench: abrir este arquivo (File > Open SQL Script)
-- e clicar no raio (Execute all). Pode rodar de novo: ele recria tudo.
-- =====================================================================
DROP DATABASE IF EXISTS loja;
CREATE DATABASE loja CHARACTER SET utf8mb4;
USE loja;

-- Tabela PAI (criar primeiro)
CREATE TABLE categorias(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

-- Tabela FILHA (tem a chave estrangeira para categorias)
CREATE TABLE produtos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL,
    categoria_id INT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

INSERT INTO categorias(nome) VALUES
('Informatica'),
('Papelaria'),
('Alimentos');

INSERT INTO produtos(nome, preco, quantidade, categoria_id) VALUES
('Mouse USB', 59.90, 25, 1),
('Teclado', 120.00, 10, 1),
('Caderno 96 folhas', 18.50, 80, 2),
('Caneta azul', 2.50, 200, 2),
('Arroz 5kg', 28.90, 40, 3);

SHOW TABLES;
DESC produtos;
SELECT p.id, p.nome, p.preco, p.quantidade, c.nome AS categoria
FROM produtos p
INNER JOIN categorias c ON c.id = p.categoria_id;
