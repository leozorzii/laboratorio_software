# 📚 Guia de Prova — Java Swing + MySQL (JDBC)

> Resumo das **aulas 07 a 10** feito em cima do código real dos projetos
> `java_swing_sql/ProjetoAulaBD` (pessoa/veículo) e `java_swing_sql/ProjetoBDescola`
> (aluno/professor/disciplina). Slides originais em [`aulas/`](aulas/).
> Para a parte de Swing com arquivo `.txt`, veja [`prova-swing-crud.md`](prova-swing-crud.md).

---

## 🧭 Índice

1. [Visão geral — as 4 camadas](#1-visão-geral--as-4-camadas)
2. [SQL que cai (MySQL Command Line)](#2-sql-que-cai)
3. [Montando o projeto no NetBeans](#3-montando-o-projeto-no-netbeans)
4. [Conexao — falar com o banco](#4-conexao)
5. [Bean — a classe que espelha a tabela](#5-bean)
6. [DAO — o CRUD em SQL](#6-dao--o-crud)
7. [PreparedStatement e ResultSet — as regras](#7-preparedstatement-e-resultset)
8. [Telas: cadastro, consulta/edição, relatório, filtro, menu](#8-telas)
9. [Relacionando tabelas (FK + ComboBox de objetos)](#9-relacionando-tabelas)
10. [Erros que eu já cometi (não repetir!)](#10-erros-que-eu-já-cometi)
11. [Perguntas teóricas prováveis](#11-perguntas-teóricas)
12. [Checklist: tabela nova relacionada do zero](#12-checklist)

---

## 1. Visão geral — as 4 camadas

```
 MySQL (tabela)  ⇄  conexao.Conexao  ⇄  DAO.XxxDAO  ⇄  Beans.Xxx  ⇄  view.FormXxx (Swing)
   onde o dado       abre a conexão     executa o SQL   carrega os    a tela: lê dos campos,
   fica salvo        (DriverManager)    (CRUD)          dados         monta o Bean, chama o DAO
```

| Pacote | Papel | Regra |
|---|---|---|
| `conexao` | `Conexao.getConexao()` devolve um `Connection` | 1 classe para o projeto todo |
| `Beans` | Classe com os **atributos = colunas** da tabela + getters/setters | **1 Bean por tabela** |
| `DAO` | *Data Access Object*: `inserir`, `getX`, `getXs`, `editar`, `excluir` | **1 DAO por tabela** |
| `view` | JFrame Forms (tela) | Tela **nunca** escreve SQL, só chama o DAO |

**Fluxo de um botão Salvar:** tela lê campos → monta Bean → `new XxxDAO().inserir(bean)` → DAO faz `INSERT`.

---

## 2. SQL que cai

```sql
SHOW DATABASES;                 -- lista os bancos
CREATE DATABASE escola;         -- cria
USE escola;                     -- seleciona (sem isso: "No database selected")
SHOW TABLES;                    -- lista tabelas do banco atual
DESC aluno;                     -- mostra as colunas da tabela

CREATE TABLE professor(
  id INT AUTO_INCREMENT PRIMARY KEY,   -- id gerado sozinho
  nome VARCHAR(50) NOT NULL,
  idade INT NOT NULL,
  disciplina VARCHAR(50) NOT NULL
);

-- tabela FILHA: cria DEPOIS da tabela pai
CREATE TABLE disciplina(
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(50) NOT NULL,
  carga_horaria INT NOT NULL,
  id_professor INT NOT NULL,
  FOREIGN KEY (id_professor) REFERENCES professor(id)
);

-- vários registros num INSERT só (não passa o id: é AUTO_INCREMENT)
INSERT INTO professor(nome, idade, disciplina) VALUES
  ('Carlos Andrade', 45, 'Banco de Dados'),
  ('Mariana Ribeiro', 38, 'Engenharia de Software');

SELECT * FROM aluno;
SELECT nome, curso FROM aluno;                       -- "liste alunos e seus cursos"
SELECT * FROM aluno WHERE nome LIKE '%ana%';         -- contém "ana"
UPDATE aluno SET nome='Ana', idade=22 WHERE id=2;    -- SEM WHERE altera TODOS
DELETE FROM aluno WHERE id=2;                        -- SEM WHERE apaga TODOS

-- JOIN (juntar tabelas relacionadas)
SELECT d.nome, d.carga_horaria, p.nome AS professor
FROM disciplina d
JOIN professor p ON p.id = d.id_professor;
```

**Chave estrangeira na prática:**
- Inserir disciplina com `id_professor` que não existe → **erro** (FK bloqueia).
- Apagar professor que tem disciplina/matrícula → **erro** (`Cannot delete or update a parent row`). Apague os filhos antes.
- Todo comando termina com `;` — esquecer junta dois comandos e dá erro de sintaxe.

Scripts completos: [`../sql/bancoAula.sql`](../sql/bancoAula.sql), [`../sql/escola.sql`](../sql/escola.sql).

---

## 3. Montando o projeto no NetBeans

1. **New Project → Java with Ant → Java Application**.
2. Botão direito no projeto → **Open in System** → criar pasta `drivers` → colocar o `mysql-connector-j-8.1.0.jar` (cópia em [`../libs/`](../libs/)).
3. Botão direito em **Libraries → Add JAR/Folder** → escolher o `.jar`.
   - Sem isso: `No suitable driver found for jdbc:mysql://...`
4. Criar os pacotes `conexao`, `Beans`, `DAO`, `view`.
5. Classe que abre primeiro: **Properties → Run → Main Class** (ex.: `view.MenuPrincipal`).

---

## 4. Conexao

```java
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    public Connection getConexao(){
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/escola?useTimezone=true&serverTimezone=UTC",
                "root", "laboratorio");   // senha do lab: "laboratorio"
            System.out.println("Conexao realizada com sucesso!");
            return conn;
        } catch (Exception e) {
            System.out.println("Erro ao conectar no DB " + e.getMessage());
            return null;
        }
    }
}
```

| Parte da URL | Significado |
|---|---|
| `jdbc:mysql://` | driver usado |
| `localhost:3306` | máquina e porta padrão do MySQL |
| `/escola` | **nome do banco** — trocar por projeto (`aula01`, `escola`) |

Teste rápido na `main`: `new Conexao().getConexao();` → tem que imprimir "Conexao realizada com sucesso!".

---

## 5. Bean

```java
package Beans;

public class Disciplina {
    private int id;
    private String nome;
    private int cargaHoraria;
    private Professor professor;   // FK vira OBJETO, não int (ver seção 9)

    // getters e setters (Alt+Insert → Getter and Setter no NetBeans)
}
```

- Tipos batem com a tabela: `INT` → `int`, `VARCHAR` → `String`, `DATE` → `java.sql.Date`/`String`.
- Para aparecer bonito em ComboBox: sobrescrever `toString()` (seção 9).

---

## 6. DAO — o CRUD

Modelo completo: [`ProjetoBDescola/src/DAO/DisciplinaDAO.java`](../java_swing_sql/ProjetoBDescola/src/DAO/DisciplinaDAO.java).

```java
public class ProfessorDAO {
    Conexao conexao;
    Connection conn;

    public ProfessorDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }

    // CREATE
    public void inserir(Professor prof){
        String sql = "INSERT INTO professor(nome,idade,disciplina) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, prof.getNome());     // 1º ?
            stmt.setInt(2, prof.getIdade());       // 2º ?
            stmt.setString(3, prof.getDisciplina());// 3º ?
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }

    // READ (um, pelo id)
    public Professor getProfessor(int id){
        String sql = "SELECT * FROM professor WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); // p/ usar rs.first()
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.first()) return null;          // não achou
            Professor p = new Professor();
            p.setId(id);
            p.setNome(rs.getString("nome"));
            p.setIdade(rs.getInt("idade"));
            p.setDisciplina(rs.getString("disciplina"));
            return p;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar: " + ex.getMessage());
            return null;
        }
    }

    // READ (lista, com filtro)
    public List<Professor> getProfessores(String nome, String disciplina){
        String sql = "SELECT * FROM professor WHERE nome LIKE ? and disciplina LIKE ?";
        List<Professor> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");   // "" → "%%" → traz todos
            stmt.setString(2, "%" + disciplina + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {                    // percorre linha a linha
                Professor p = new Professor();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setIdade(rs.getInt("idade"));
                p.setDisciplina(rs.getString("disciplina"));
                lista.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar: " + ex.getMessage());
        }
        return lista;
    }

    // UPDATE — o id vai por ÚLTIMO (é o último ?)
    public void editar(Professor prof){
        String sql = "UPDATE professor SET nome=?, idade=?, disciplina=? WHERE id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, prof.getNome());
            stmt.setInt(2, prof.getIdade());
            stmt.setString(3, prof.getDisciplina());
            stmt.setInt(4, prof.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar: " + ex.getMessage());
        }
    }

    // DELETE
    public void excluir(int id){
        String sql = "DELETE FROM professor WHERE id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir: " + ex.getMessage());
        }
    }
}
```

**Imports do DAO:** `java.sql.*` (ou `Connection`, `PreparedStatement`, `ResultSet`, `SQLException`), `java.util.ArrayList`, `java.util.List`, `conexao.Conexao`, `Beans.Xxx`.

---

## 7. PreparedStatement e ResultSet

| Regra | Exemplo |
|---|---|
| Os `?` são numerados **a partir de 1**, na ordem em que aparecem no SQL | `VALUES (?,?,?)` → `set(1)`, `set(2)`, `set(3)` |
| O `setX` tem que bater com o tipo da coluna | `VARCHAR` → `setString`, `INT` → `setInt` |
| O `getX` também | `rs.getString("placa")`, não `rs.getInt("placa")` |
| `getX("nome_da_coluna")` usa o nome **do banco** | `rs.getInt("id_professor")`, não `"idProfessor"` |
| `executeQuery()` para SELECT (devolve `ResultSet`) | `ResultSet rs = stmt.executeQuery();` |
| `execute()` / `executeUpdate()` para INSERT/UPDATE/DELETE | `stmt.execute();` |
| `rs.next()` → anda uma linha; use em `while` para listas | `while (rs.next()) { ... }` |
| `rs.first()` → vai para a 1ª linha; precisa de `TYPE_SCROLL_INSENSITIVE` | consulta por id |

**Por que `?` e não concatenar string?** Evita **SQL Injection** (alguém digitar `' OR 1=1 --`) e trata aspas/tipos sozinho.

---

## 8. Telas

### Cadastro (botão Salvar)
```java
Professor p = new Professor();
p.setNome(txt_nome.getText());
p.setIdade(Integer.parseInt(txt_idade.getText()));  // texto → int
p.setDisciplina(txt_disciplina.getText());
new ProfessorDAO().inserir(p);
limparFormulario();
```

### Consulta por ID + Atualizar + Excluir
```java
// Buscar
Professor p = new ProfessorDAO().getProfessor(Integer.parseInt(txt_id.getText()));
if (p == null) {
    limparFormulario();
    JOptionPane.showMessageDialog(this, "Professor não encontrado");
} else {
    txt_nome.setText(p.getNome());
    txt_idade.setText(p.getIdade() + "");   // int → texto
}

// Atualizar: monta o Bean COM o id e chama editar()
// Excluir:   new ProfessorDAO().excluir(Integer.parseInt(txt_id.getText()));
```
- RadioButton: `rdb_masculino.setSelected(true)` / `rdb_masculino.isSelected()` (agrupar num `ButtonGroup`).
- ComboBox de texto: `cmb.setSelectedItem("Português")` / `cmb.getSelectedItem().toString()`.

### Relatório (JTable)
```java
public void preencheTabela(){
    List<Professor> lista = new ProfessorDAO().getProfessores(txt_nome.getText(), "");
    DefaultTableModel modelo = (DefaultTableModel) tbl_professores.getModel();
    modelo.setNumRows(0);                  // LIMPA antes, senão duplica as linhas
    for (Professor p : lista) {
        modelo.addRow(new Object[]{ p.getId(), p.getNome(), p.getIdade(), p.getDisciplina() });
    }
}
```
- **Chamar `preencheTabela()` no construtor**, depois de `initComponents()`, senão a tela abre vazia.
- **Filtro:** chamar `preencheTabela()` num evento do campo (`keyReleased` no TextField, ou `actionPerformed` de um botão/combo).

### Menu principal
- JFrame Form `MenuPrincipal` + componente **Menu Bar** → Menus (`Cadastros`, `Relatórios`) → **Menu Items**.
- No evento do item: `new FormCadastroProfessor().setVisible(true);`
- Item **Sair**: `System.exit(0);`
- Nas telas abertas pelo menu: **`defaultCloseOperation = DISPOSE`** (fecha só a janela). Com `EXIT_ON_CLOSE` fechar uma tela derruba o programa inteiro.

---

## 9. Relacionando tabelas

Exemplos reais: Pessoa → Veículo ([`ProjetoAulaBD`](../java_swing_sql/ProjetoAulaBD/src)) e Professor → Disciplina ([`ProjetoBDescola`](../java_swing_sql/ProjetoBDescola/src)).

**1. No banco:** coluna `id_professor INT NOT NULL` + `FOREIGN KEY (id_professor) REFERENCES professor(id)`.

**2. No Bean:** guarda o **objeto**, não o int.
```java
private Professor professor;
```

**3. No DAO:** gravar usa o id do objeto; ler busca o objeto pelo id.
```java
stmt.setInt(3, disciplina.getProfessor().getId());                   // inserir/editar

ProfessorDAO profDAO = new ProfessorDAO();                            // getDisciplina
d.setProfessor(profDAO.getProfessor(rs.getInt("id_professor")));
```

**4. Na tela — ComboBox de objetos:**
- Propriedades do combo: **model** → apagar os itens padrão; **Code → Type Parameters** → `<Professor>`.
- Preencher no construtor:
```java
public void preencherComboProfessores(){
    List<Professor> lista = new ProfessorDAO().getProfessores("", "");
    cmb_professores.removeAllItems();          // tira "Item 1, Item 2..."
    for (Professor p : lista) cmb_professores.addItem(p);
}
```
- Salvar: `Professor p = (Professor) cmb_professores.getSelectedItem();`

**5. `toString()` — o que aparece no combo.** Sem ele aparece `Beans.Professor@1b6d3586`.
```java
@Override
public String toString() { return this.id + " - " + this.nome; }
```

**6. `equals()` — para a edição selecionar o item certo.**
`cmb.setSelectedItem(d.getProfessor())` compara com `equals`. O padrão de `Object` compara **referência** (mesmo objeto na memória); o professor que veio do `getDisciplina` é outro objeto, então não acha. Solução: comparar pelo id (e gerar `hashCode` junto — **Alt+Insert → equals() and hashCode()**).
```java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    return this.id == ((Professor) obj).id;
}
@Override
public int hashCode() { return Integer.hashCode(this.id); }
```

---

## 10. Erros que eu já cometi

Achados na revisão do Veículo — cada um é um ponto perdido fácil.

| Erro | Sintoma | Certo |
|---|---|---|
| Todos os `setX` com índice 1 | `No value specified for parameter 2` | 1, 2, 3… na ordem dos `?` |
| Gravar `veiculo.getId()` no lugar do id da FK | grava dono errado / erro de FK | `veiculo.getPessoaid().getId()` |
| `rs.getInt("placa")` em coluna VARCHAR | não compila / erro de conversão | `rs.getString` |
| Método declarado **dentro** de outro método | arquivo inteiro não compila | fechar a `}` antes |
| Nome do método com typo (`preenhcher…`) | `cannot find symbol` | mesmo nome na declaração e na chamada |
| Faltou `import` (`List`, `ResultSet`, `Beans.X`, `DAO.X`, `DefaultTableModel`) | `cannot find symbol` | **Ctrl+Shift+I** (Fix Imports) |
| Setter com maiúscula diferente (`setPessoaId` × `setPessoaid`) | `cannot find symbol` | Java diferencia maiúsculas |
| `SQLExeption` | `cannot find symbol` | `SQLException` |
| Método no DAO errado (`getPessoas` no `VeiculoDAO`) | tela chama `pDAO.getPessoas()` e não acha | cada método no DAO da sua tabela |
| Não chamar `preencheTabela()` no construtor | relatório abre vazio | chamar depois de `initComponents()` |
| `EXIT_ON_CLOSE` nas telas | fechar uma tela fecha tudo | `DISPOSE_ON_CLOSE` |
| Excluir pai que tem filhos | exceção de FK, só no console | excluir filhos antes / avisar com `JOptionPane` |
| Placa maior que `VARCHAR(7)` | `Data too long for column` | validar tamanho / aumentar coluna |
| Combo com item `" "` padrão | aparece item vazio | `removeAllItems()` antes de preencher |
| `;` faltando no `.sql` | erro de sintaxe no comando seguinte | todo comando termina em `;` |
| Sem `equals` no Bean | edição não seleciona o item do combo | `equals` + `hashCode` por id |

---

## 11. Perguntas teóricas

- **O que é um Bean?** Classe que representa uma tabela: atributos = colunas, com getters/setters.
- **O que é DAO?** *Data Access Object*: classe que concentra o acesso ao banco (o SQL) de uma tabela. Separa regra de tela da persistência.
- **Por que um DAO/Bean por tabela?** Organização e responsabilidade única: cada classe cuida de uma coisa.
- **PreparedStatement × Statement?** Prepared usa parâmetros `?`: evita SQL Injection, trata tipos/aspas, reaproveita o comando.
- **`execute` × `executeQuery`?** `executeQuery` é para SELECT e devolve `ResultSet`; `execute`/`executeUpdate` para INSERT/UPDATE/DELETE.
- **O que é chave primária?** Identifica cada linha de forma única (`id ... PRIMARY KEY`). `AUTO_INCREMENT` gera o valor.
- **O que é chave estrangeira?** Coluna que referencia a PK de outra tabela, garantindo **integridade referencial** (não aponta para registro inexistente nem deixa apagar o pai com filhos).
- **Para que serve o driver (`.jar`)?** Implementação JDBC que sabe falar com o MySQL. Sem ele: `No suitable driver`.
- **Para que `toString()` no combo?** Define o texto exibido do objeto.
- **Para que `equals()`?** Comparar objetos por conteúdo (id) e não por referência; usado pelo `setSelectedItem`.
- **DISPOSE × EXIT?** DISPOSE fecha só a janela; EXIT encerra a aplicação (JVM).
- **Por que `setNumRows(0)`?** Limpar a tabela antes de preencher de novo, senão as linhas se acumulam.

---

## 12. Checklist

Pedido típico: *"crie a tabela X com FK para Y, cadastro que busca Y e tela de edição"*.

- [ ] **SQL:** `CREATE TABLE x(... id_y INT NOT NULL, FOREIGN KEY (id_y) REFERENCES y(id));` rodar no banco certo (`USE`).
- [ ] **Bean `Beans/X.java`:** atributos + getters/setters, FK como objeto `Y`.
- [ ] **Bean `Y`:** `toString()` e `equals()`/`hashCode()` por id.
- [ ] **`DAO/XDAO.java`:** construtor com `Conexao`, `inserir`, `getX(id)` (com `rs.first()`), `getXs()`, `editar`, `excluir`; índices `?` conferidos.
- [ ] **`DAO/YDAO`:** tem um método que lista todos (para o combo)?
- [ ] **Tela cadastro:** campos + combo `<Y>` sem itens padrão; `preencherCombo()` no construtor; Salvar monta Bean e chama `inserir`; `limparFormulario()`.
- [ ] **Tela consulta/edição:** `txt_id` + Buscar (`getX`, trata `null`) + `setSelectedItem(x.getY())`; Atualizar chama `editar` com o id.
- [ ] **Imports** (Ctrl+Shift+I) e **DISPOSE_ON_CLOSE** nas telas.
- [ ] **Menu:** item novo com `new FormX().setVisible(true)`.
- [ ] **Testar:** inserir, buscar, trocar o Y e atualizar, conferir com `SELECT * FROM x;` no MySQL.
