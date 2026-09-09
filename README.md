# Laboratorio de Software

Repositorio de exercicios e projetos desenvolvidos na disciplina de **Laboratorio de Software**, com foco em programacao orientada a objetos com Java e interfaces graficas com Java Swing.

## Conteudos Estudados

### Programacao Orientada a Objetos (revisao/)

Exercicios de revisao dos pilares de POO com projeto Maven:

- **Encapsulamento** — `ContaCorrente`: classe com saldo privado, metodos `depositar`, `sacar` (com validacao) e `definirSaldoInicial`
- **Heranca** — `Pessoa` / `Professor`: heranca simples com `extends`, atributos `protected`, sobrescrita de `toString()`
- **Classes Abstratas** — `FormaGeometrica` / `Triangulo`: classe abstrata com metodo `calcularArea()` implementado nas subclasses
- **Polimorfismo e Abstracao** — `MetodoPagamento` / `PIXPagamento`, `CartaoCreditoPagamento`, `PayPalPagamento`: sistema de pagamentos com processamento polimorfico
- **Sobrecarga de Metodos** — `Casa`: metodo `calcularPreco()` sobrecarregado para aceitar diferentes parametros
- **Interfaces** — `ICalculadora`: contrato com operacoes matematicas (somar, subtrair, multiplicar, dividir, raiz quadrada, potencia, log10)

### Java Swing — Interfaces Graficas

#### CaixaEletronico/
Simulacao de caixa eletronico com Java Swing (JFrame). Funcionalidades:
- Depositar e sacar valores
- Consultar/ocultar saldo
- Validacao de entrada do usuario

#### AulaOutrosComponentes/
Formularios de estudo com componentes Swing diversos: `JTextField`, `JTextArea`, `JButton`, `JScrollPane`.

#### CadastroAluno/
CRUD completo de alunos com interface grafica:
- Cadastrar, editar e excluir alunos
- Tabela (`JTable`) com `DefaultTableModel`
- Persistencia em arquivo texto (`.txt`) com separador `;`
- Campos: nome, data nascimento, sexo, matricula, curso, CPF, endereco completo, telefone
- Componentes: `JTextField`, `JRadioButton`, `JComboBox`, `JTable`

#### Biblioteca/
Sistema de gerenciamento de biblioteca com Swing:
- CRUD de livros (titulo, autor, ano, genero)
- Emprestimo e devolucao com controle de status e nome do leitor
- Persistencia em arquivo texto
- Componentes: `JTable`, `JComboBox`, `JButton`, `JOptionPane`

#### cadastroProduto/
Cadastro de produtos com interface grafica:
- CRUD de produtos (nome, categoria, preco, quantidade, disponibilidade)
- Persistencia em arquivo texto
- Componentes: `JTable`, `JComboBox`, `JRadioButton`

## Estrutura do Projeto

```
laboratorio_software/
├── revisao/                    # POO: heranca, abstracao, polimorfismo, interfaces
├── CaixaEletronico/            # Swing: simulacao de caixa eletronico
├── AulaOutrosComponentes/      # Swing: estudo de componentes
├── CadastroAluno/              # Swing + CRUD + persistencia em arquivo
├── Biblioteca/                 # Swing + CRUD + emprestimo de livros
└── cadastroProduto/            # Swing + CRUD + cadastro de produtos
```

## Tecnologias

- **Java** (SE)
- **Java Swing** (JFrame, JTable, JOptionPane, GroupLayout)
- **Maven** (projeto revisao)
- **NetBeans** (GUI Builder)
- **Persistencia em arquivos texto** (.txt com BufferedReader/BufferedWriter)

## Proximos Passos

- Integracao com **banco de dados** (JDBC / JPA) para substituir a persistencia em arquivo texto
- Aplicacoes com Java Swing conectadas a banco de dados relacional

## Autor

Leonardo Zorzi
