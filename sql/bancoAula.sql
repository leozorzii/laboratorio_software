CREATE DATABASE aula01;
USE aula01;
SHOW databases;

CREATE TABLE pessoa(
id INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(50) NOT NULL,
sexo VARCHAR(1) NOT NULL,
idioma VARCHAR(50) NOT NULL
);

SHOW tables;
DESC pessoa;

INSERT INTO pessoa (nome, sexo, idioma)
VALUES 
('Cortoius', 'M', 'Francês'),
('Luis', 'M', 'Português'),
('Ricardo', 'M', 'Alemão'),
('Neymar', 'M', 'Português'),
('Messi', 'M', 'Turco');

SELECT *
FROM PESSOA;
-- adiciona tabela veiculo
CREATE TABLE veiculo(
id INT AUTO_INCREMENT PRIMARY KEY,
modelo VARCHAR(30),
placa VARCHAR(7),
id_pessoa INT NOT NULL,
foreign key(id_pessoa) REFERENCES pessoa(id)
);







