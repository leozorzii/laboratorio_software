# Laboratorio de Software

Repositorio de exercicios e projetos da disciplina de **Laboratorio de Software**: Java Swing (interfaces graficas), primeiro com persistencia em arquivo texto e depois com banco de dados **MySQL via JDBC**.

> 📚 **Estudando para a prova?** Comece por [`docs/`](docs/) — guia de prova, mapa das atividades e slides das aulas.

## Estrutura

```
laboratorio_software/
├── docs/                         # material de estudo
│   ├── prova-java-banco-de-dados.md   # guia de prova: Swing + MySQL (aulas 07–10)
│   ├── prova-swing-crud.md            # guia de prova: Swing + CRUD em .txt
│   ├── atividades.md                  # atividade da aula → onde esta resolvida
│   └── aulas/                         # slides das aulas 07–10 (PDF)
├── java_swing/                   # Parte 1: Swing + persistencia em arquivo .txt
│   ├── AulaOutrosComponentes/
│   ├── CaixaEletronico/
│   ├── CadastroAluno/
│   ├── Biblioteca/
│   ├── cadastroProduto/
│   └── GerenciamentoEventos/
├── java_swing_sql/               # Parte 2: Swing + MySQL (JDBC)
│   ├── ProjetoAulaBD/                 # projeto das aulas: pessoa e veiculo (banco aula01)
│   └── ProjetoBDescola/               # atividades: aluno, professor, disciplina (banco escola)
├── sql/                          # scripts dos bancos
│   ├── bancoAula.sql                  # banco aula01: pessoa, veiculo
│   └── escola.sql                     # banco escola: aluno, professor, matricula, disciplina
└── libs/
    └── mysql-connector-j-8.1.0.jar    # driver JDBC (copiar para drivers/ de um projeto novo)
```

## Parte 1 — Java Swing com arquivo texto (`java_swing/`)

| Projeto | O que tem |
|---|---|
| `AulaOutrosComponentes` | Estudo de componentes: `JTextField`, `JTextArea`, `JButton`, `JScrollPane`; gravacao de pessoas em `.txt` |
| `CaixaEletronico` | Depositar, sacar, mostrar/ocultar saldo, validacao de entrada |
| `CadastroAluno` | CRUD de alunos com `JTable` + `DefaultTableModel`, `JRadioButton`, `JComboBox`, arquivo `;` |
| `Biblioteca` | CRUD de livros + emprestimo/devolucao com status e leitor |
| `cadastroProduto` | CRUD de produtos (nome, categoria, preco, quantidade, disponibilidade) |
| `GerenciamentoEventos` | CRUD de eventos (nome, data, local, tipo, modalidade, situacao) |

Padrao: `ArrayList` em memoria → grava no `.txt` → recarrega a `JTable`. Detalhes em [`docs/prova-swing-crud.md`](docs/prova-swing-crud.md).

## Parte 2 — Java Swing + MySQL (`java_swing_sql/`)

Arquitetura em pacotes: `conexao` (Connection) → `DAO` (SQL com `PreparedStatement`) → `Beans` (uma classe por tabela) → `view` (JFrame Forms).

| Aula | Conteudo | ProjetoAulaBD | ProjetoBDescola |
|---|---|---|---|
| 07 | Conexao, Bean, DAO, INSERT | Cadastro de pessoa | Cadastro de aluno e professor |
| 08 | Consultar por ID, editar, excluir | Consulta de pessoa | Consulta de aluno e professor |
| 09 | Relatorio (`JTable`), filtro `LIKE`, menu | Relatorios + `Menu` | Relatorios com filtro + `MenuPrincipal` |
| 10 | Tabelas relacionadas (FK), `JComboBox` de objetos, `toString`/`equals` | Veiculo → Pessoa | Disciplina → Professor |

Mapa completo arquivo a arquivo: [`docs/atividades.md`](docs/atividades.md).

## Como rodar

1. **Banco:** no MySQL Command Line Client (usuario `root`, senha do lab `laboratorio`), rodar o script do projeto:
   - `ProjetoAulaBD` → [`sql/bancoAula.sql`](sql/bancoAula.sql) (cria `aula01`)
   - `ProjetoBDescola` → [`sql/escola.sql`](sql/escola.sql) (cria `escola`)
2. **NetBeans:** File → Open Project → `java_swing_sql/<projeto>`. O driver ja esta em `drivers/` e nas Libraries.
3. Conferir usuario/senha em `src/conexao/Conexao.java` e rodar (F6). A classe principal e o menu (`view.Menu` / `view.MenuPrincipal`).

## Tecnologias

- **Java** (SE) + **Java Swing** (JFrame, JTable, JComboBox, JMenuBar, JOptionPane, GroupLayout)
- **MySQL** + **JDBC** (`mysql-connector-j` 8.1.0, `PreparedStatement`, `ResultSet`)
- **NetBeans** (Java with Ant, GUI Builder)
- Persistencia em arquivo texto (`BufferedReader` / `BufferedWriter`) na parte 1

## Autor

Leonardo Zorzi
