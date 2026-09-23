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



CREATE DATABASE escola;
USE escola;

CREATE TABLE aluno(
id INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(50) NOT NULL,
idade INT NOT NULL,
curso VARCHAR(50) NOT NULL
);
INSERT INTO aluno(nome, idade, curso)
VALUES 
('Luizao', 20, 'Sistemas de Informação'),
('Ana Silva', 21, 'Ciência da Computação'),
('Bruno Costa', 23, 'Sistemas de Informação'),
('Carla Souza', 19, 'Sistemas de Informação'),
('Diego Santos', 22, 'Ciência da Computação'),
('Fernanda Lima', 20, 'Sistemas de Informação');

CREATE TABLE professor(
id INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(50) NOT NULL,
idade INT NOT NULL,
disciplina VARCHAR(50) NOT NULL
);
INSERT INTO professor(nome, idade, disciplina)
VALUES
('Carlos Andrade', 45, 'Banco de Dados'),
('Mariana Ribeiro', 38, 'Engenharia de Software'),
('Roberto Alencar', 52, 'Programação Orientada a Objetos'),
('Patricia Melo', 29, 'Sistemas Operacionais'),
('Ricardo Ramos', 41, 'Redes de Computadores');

CREATE TABLE matricula(
id INT auto_increment PRIMARY KEY,
id_aluno  INT NOT NULL,
id_professor INT NOT NULL,
data_matricula DATE,

FOREIGN KEY(id_aluno) REFERENCES aluno(id),

FOREIGN KEY (id_professor) REFERENCES professor(id)
);

INSERT INTO matricula(id_aluno, id_professor, data_matricula)
VALUES
(1 ,1 , '2025-01-05'),
(2 ,3 , '2026-03-15'),
(5 ,2 , '2025-04-20'),
(1 ,4 , '2026-06-25');

SELECT * FROM matricula 




