# Atividades × onde estão resolvidas

Mapa das atividades das aulas de **Java + Banco de Dados** (slides em [`aulas/`](aulas/)) para o código.
Caminhos relativos à raiz do repositório.

- `AulaBD` = `java_swing_sql/ProjetoAulaBD/src` (banco `aula01`, script [`sql/bancoAula.sql`](../sql/bancoAula.sql))
- `Escola` = `java_swing_sql/ProjetoBDescola/src` (banco `escola`, script [`sql/escola.sql`](../sql/escola.sql))

## Aula 07 — Banco de dados + Java ([pdf](aulas/aula07-banco-de-dados-java.pdf))

| Atividade | Onde |
|---|---|
| Criar tabela `pessoa` no MySQL | `sql/bancoAula.sql` |
| Exercícios 1–8: banco `escola`, tabelas `aluno`, `professor`, `matricula` + inserts | `sql/escola.sql` |
| Exercícios 9–10: listar alunos e cursos / professores e disciplinas | `SELECT nome, curso FROM aluno;` e `SELECT nome, disciplina FROM professor;` (não estão no script — ver [guia](prova-java-banco-de-dados.md#2-sql-que-cai)) |
| Conexão + Bean + DAO + insert | `AulaBD`: `conexao/Conexao.java`, `Beans/Pessoa.java`, `DAO/PessoaDAO.java` |
| Atividade 1: tela de cadastro de pessoas | `AulaBD`: `view/FormCadastroPessoa` |
| Atividade 2: projeto `escola`, cadastro de alunos e professores | `Escola`: `view/FormCadastroAluno`, `view/FormCadastroProfessor`, `DAO/AlunoDAO.java`, `DAO/ProfessorDAO.java` |

## Aula 08 — Consultar, editar e deletar ([pdf](aulas/aula08-consultar-editar-deletar.pdf))

| Atividade | Onde |
|---|---|
| `getPessoa(id)`, `editar`, `excluir` no DAO | `AulaBD`: `DAO/PessoaDAO.java` |
| Tela de consulta/edição/exclusão (radio para sexo, combo para idioma) | `AulaBD`: `view/FormConsultaPessoa` |
| Atividade: consultar e editar alunos e professores | `Escola`: `view/FormConsultaAluno`, `view/FormConsultaProfessor` |

## Aula 09 — Consultas, menu e filtro ([pdf](aulas/aula09-consultas-menu-filtro.pdf))

| Atividade | Onde |
|---|---|
| Relatório de todas as pessoas (JTable) | `AulaBD`: `view/RelatorioPessoas` |
| Menu principal (Cadastro / Relatórios / Sair), `DISPOSE` | `AulaBD`: `view/Menu` |
| Filtro por nome e sexo (`LIKE`) | `AulaBD`: `view/RelatorioPessoasComFIltro`, `PessoaDAO.getPessoas(nome, sexo)` |
| Atividade: menu + uma tela por tabela com filtro de nome | `Escola`: `view/MenuPrincipal`, `view/RelatorioAlunos(Filtro)`, `view/RelatorioProfessores(Filtro)` |

## Aula 10 — Relacionando tabelas ([pdf](aulas/aula10-relacionando-tabelas.pdf))

| Atividade | Onde |
|---|---|
| Tabela `veiculo` com FK para `pessoa` | `sql/bancoAula.sql` |
| Bean + DAO de veículo | `AulaBD`: `Beans/Veiculo.java`, `DAO/VeiculoDAO.java` |
| Cadastro de veículo com combo de pessoas (`toString`) | `AulaBD`: `view/FormCadastroVeiculo` |
| Atividade: consulta por ID e edição do veículo (`equals`) | `AulaBD`: `view/FormConsultaVeiculos` |
| Relatório de veículos | `AulaBD`: `view/RelatorioVeiculos` |
| **Atividade 2:** tabela `disciplina` (nome, carga horária, FK professor) | `sql/escola.sql` |
| **Atividade 2:** cadastro de disciplina buscando professores | `Escola`: `Beans/Disciplina.java`, `DAO/DisciplinaDAO.java`, `view/FormCadastroDisciplina` |
| **Atividade 2:** tela de edição que troca o professor | `Escola`: `view/FormConsultaDisciplina` (menu: Cadastros → Consulta Disciplinas) |

## Pendências conhecidas

- As telas de veículo do `AulaBD` ainda não estão no `view/Menu` e usam `EXIT_ON_CLOSE` (fecham o programa todo).
- Melhorias sugeridas na revisão (não obrigatórias): fechar conexões, mostrar erros com `JOptionPane`, validar campos numéricos — ver seção 10 do [guia](prova-java-banco-de-dados.md#10-erros-que-eu-já-cometi).
