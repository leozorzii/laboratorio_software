# Guia de Replicação para a Prova — CRUD Swing + MySQL com tabelas relacionadas

> Objetivo: na prova aparece **uma variação** de "CRUD Swing + MySQL com duas (ou três) tabelas
> relacionadas". Este guia traz um **molde genérico** (`Pai` / `Filho`) completo e copiável, a tabela
> de **troca de nomes**, um **catálogo de variações** e as **mensagens de erro** com a solução.
>
> Base real (compilada e testada): [`../java_swing_sql/Produtos`](../java_swing_sql/Produtos) com o script
> [`../sql/produtos.sql`](../sql/produtos.sql). Padrão das aulas (GUI Builder):
> [`ProjetoAulaBD`](../java_swing_sql/ProjetoAulaBD) e [`ProjetoBDescola`](../java_swing_sql/ProjetoBDescola).
> Complementa [`prova-java-banco-de-dados.md`](prova-java-banco-de-dados.md),
> [`prova-swing-crud.md`](prova-swing-crud.md) e [`atividades.md`](atividades.md).

---

## Índice

1. [Como usar este guia e tabela de substituição](#1-como-usar-este-guia-e-tabela-de-substituição)
2. [Ordem de ataque na prova](#2-ordem-de-ataque-na-prova)
3. [SQL: script, tipos, variações e consultas](#3-sql-script-tipos-variações-e-consultas)
4. [MySQL Workbench no laboratório](#4-mysql-workbench-no-laboratório)
5. [NetBeans passo a passo](#5-netbeans-passo-a-passo)
6. [Código-modelo genérico: Conexao, Beans e DAOs](#6-código-modelo-genérico-conexao-beans-e-daos)
7. [Telas modelo](#7-telas-modelo)
8. [Catálogo de variações de prova](#8-catálogo-de-variações-de-prova)
9. [Erros e soluções](#9-erros-e-soluções)
10. [Checklist final e perguntas teóricas](#10-checklist-final-e-perguntas-teóricas)
11. [Anexo: cola de uma página](#11-anexo-cola-de-uma-página)

---

## 1. Como usar este guia e tabela de substituição

**Estratégia:** não escreva do zero. Leia o enunciado, ache qual é o **Pai** (lado "1", ex.: Categoria) e qual é
o **Filho** (lado "N", que tem a chave estrangeira, ex.: Produto), copie o molde da [seção 6](#6-código-modelo-genérico-conexao-beans-e-daos) e troque os nomes.

No molde genérico os nomes são `Pai`/`Filho`, tabelas `pais`/`filhos`, FK `pai_id`, banco `meubanco`.

| Conceito | Molde genérico | Produtos (repo real) | Alunos / Cursos | Livros / Autores | Veículos / Pessoas |
|---|---|---|---|---|---|
| Banco | `meubanco` | `loja` | `faculdade` | `biblioteca` | `aula01` (real) |
| Pai (classe) | `Pai` | `Categoria` | `Curso` | `Autor` | `Pessoa` |
| Filho (classe) | `Filho` | `Produto` | `Aluno` | `Livro` | `Veiculo` |
| Tabela pai | `pais` | `categorias` | `cursos` | `autores` | `pessoa` |
| Tabela filha | `filhos` | `produtos` | `alunos` | `livros` | `veiculo` |
| Coluna FK | `pai_id` | `categoria_id` | `curso_id` | `autor_id` | `id_pessoa` |
| Campo double | `preco` | `preco` | `mensalidade` | `preco` | — |
| Campo int | `quantidade` | `quantidade` | `idade` | `paginas` | `ano` |
| Campo texto | `nome` | `nome` | `nome` | `titulo` | `modelo`, `placa` |
| Variável getter do pai | `getPai()` | `getCategoria()` | `getCurso()` | `getAutor()` | `getPessoa()` |
| Combo | `cmb_pai` | `cmb_categorias` | `cmb_cursos` | `cmb_autores` | `cmb_pessoas` |
| Tabela Swing | `tbl_filhos` | `tbl_produtos` | `tbl_alunos` | `tbl_livros` | `tbl_veiculos` |

**Como trocar rápido no NetBeans** (Edit > Replace in Projects, `Ctrl+Shift+H`; marque **Match Case**).
A ordem importa porque `pai` está dentro de `pais`: troque **o plural antes do singular**.

| Ordem | Procurar | Trocar por (exemplo Alunos/Cursos) |
|---|---|---|
| 1 | `Pais` | `Cursos` |
| 2 | `pais` | `cursos` |
| 3 | `Pai` | `Curso` |
| 4 | `pai` | `curso` |
| 5 | `Filhos` | `Alunos` |
| 6 | `filhos` | `alunos` |
| 7 | `Filho` | `Aluno` |
| 8 | `filho` | `aluno` |
| 9 | `meubanco` | `faculdade` |

Depois apague/renomeie os campos que o enunciado não pede (`preco`, `quantidade`) e acrescente os que ele pede.
Plurais irregulares (`autores`, `Autores`) pedem o ajuste manual nos passos 1 e 2.
Se o NetBeans renomear só texto e não o nome do arquivo, renomeie os arquivos `.java` com botão direito > **Refactor > Rename**.

Exemplo completo "Produtos para Alunos/Cursos":

| Produtos (real) | Alunos/Cursos |
|---|---|
| `Categoria` (`id`, `nome`) | `Curso` (`id`, `nome`) |
| `Produto` (`nome`, `preco`, `quantidade`, `categoria`) | `Aluno` (`nome`, `idade`, `mensalidade`, `curso`) |
| `SELECT ... FROM produtos p INNER JOIN categorias c ...` | `SELECT ... FROM alunos a INNER JOIN cursos c ON c.id = a.curso_id` |
| tela "Cadastro de Produtos" | tela "Cadastro de Alunos" |

---

## 2. Ordem de ataque na prova

Tempo para uma prova de cerca de 2 horas (ajuste proporcionalmente).

| # | Etapa | Tempo | Pronto quando |
|---|---|---|---|
| 0 | Ler o enunciado inteiro, marcar Pai/Filho, campos e tipos, telas pedidas | 5 min | rabisco das tabelas no papel |
| 1 | **SQL**: escrever o script, rodar no Workbench | 10 min | `SELECT` com `JOIN` devolve linhas |
| 2 | **Projeto + driver**: New Project, pacotes, jar nas Libraries | 5 min | jar aparece em Libraries |
| 3 | **Conexao**: copiar, trocar banco, testar com `main` | 5 min | imprime "Conexao realizada com sucesso!" |
| 4 | **Beans**: Pai (`toString`+`equals`) e Filho | 10 min | compila |
| 5 | **DAOs**: PaiDAO e FilhoDAO | 20 min | compila; teste rápido no `main` |
| 6 | **Telas**: cadastro, consulta/edição, relatório (nessa ordem) | 40 min | cada tela salva/lê de verdade |
| 7 | **Menu** ligando as telas e Sair | 5 min | abre as telas |
| 8 | **Teste final**: inserir, buscar, editar, excluir, filtrar; conferir no Workbench | 10 min | tudo OK |
| 9 | Sobra: extras pedidos (total, login, N:N...), organizar, salvar | resto | — |

Regras de ouro:

- Faça a **parte que dá nota primeiro** (CRUD funcionando) e só depois enfeite.
- Teste cada camada antes de subir para a próxima (`main` da Conexao, depois `main` do DAO imprimindo a lista).
- Se travar numa tela, **vá para a próxima** e volte depois.
- Salve/compile (`Shift+F11` Clean and Build) a cada etapa.
- Teste rápido de DAO sem tela: `System.out.println(new FilhoDAO().getFilhos().size());` num `main` temporário.

---

## 3. SQL: script, tipos, variações e consultas

### 3.1 Modelo de script (pai antes do filho)

Igual a [`../sql/produtos.sql`](../sql/produtos.sql), com nomes genéricos. Pode rodar de novo: ele recria tudo.

```sql
-- ARQUIVO: sql/meubanco.sql
DROP DATABASE IF EXISTS meubanco;
CREATE DATABASE meubanco CHARACTER SET utf8mb4;
USE meubanco;

-- Tabela PAI (criar primeiro)
CREATE TABLE pais(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

-- Tabela FILHA (tem a chave estrangeira para pais)
CREATE TABLE filhos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL,
    pai_id INT NOT NULL,
    FOREIGN KEY (pai_id) REFERENCES pais(id)
);

INSERT INTO pais(nome) VALUES ('Informatica'), ('Papelaria'), ('Alimentos');

INSERT INTO filhos(nome, preco, quantidade, pai_id) VALUES
('Mouse USB', 59.90, 25, 1),
('Teclado', 120.00, 10, 1),
('Caderno 96 folhas', 18.50, 80, 2),
('Caneta azul', 2.50, 200, 2),
('Arroz 5kg', 28.90, 40, 3);

SHOW TABLES;
DESC filhos;
SELECT f.id, f.nome, f.preco, f.quantidade, p.nome AS pai
FROM filhos f
INNER JOIN pais p ON p.id = f.pai_id;
```

Ordem das coisas: `DROP DATABASE` / `CREATE DATABASE` / `USE` / **tabelas pai** / **tabelas filhas** / `INSERT` do pai **antes** do filho
(senão a FK recusa) / `SELECT` de conferência. Todo comando termina em `;`.

### 3.2 Tipos de coluna e o par Java

| Dado | Tipo MySQL | Atributo Java | `PreparedStatement` | `ResultSet` |
|---|---|---|---|---|
| id, quantidade, idade | `INT` | `int` | `setInt(i, v)` | `getInt("col")` |
| nome, placa, sexo (`'M'`/`'F'`) | `VARCHAR(n)` | `String` | `setString(i, v)` | `getString("col")` |
| preço, salário | `DECIMAL(10,2)` | `double` (ou `BigDecimal`) | `setDouble(i, v)` | `getDouble("col")` |
| data | `DATE` | `java.sql.Date` ou `LocalDate` | `setDate(i, v)` | `getDate("col")` |
| ativo, sim/não | `BOOLEAN` (= `TINYINT(1)`) | `boolean` | `setBoolean(i, v)` | `getBoolean("col")` |
| texto longo | `TEXT` | `String` | `setString` | `getString` |

### 3.3 Variações de modelagem

**1:N (o padrão):** a FK fica no lado "N". Um curso tem vários alunos: `alunos.curso_id` referencia `cursos.id`.

**N:N com tabela de ligação** (aluno cursa várias disciplinas; disciplina tem vários alunos). Foi o que `matricula` fez em `../sql/escola.sql`.

```sql
CREATE TABLE alunos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);
CREATE TABLE disciplinas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    carga_horaria INT NOT NULL
);
CREATE TABLE matriculas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_disciplina INT NOT NULL,
    data_matricula DATE,
    FOREIGN KEY (id_aluno) REFERENCES alunos(id),
    FOREIGN KEY (id_disciplina) REFERENCES disciplinas(id),
    UNIQUE (id_aluno, id_disciplina)      -- não matricular duas vezes na mesma disciplina
);
```

**Duas FKs na mesma tabela** (mesma tabela referenciando outra duas vezes, ex.: voo com origem e destino na tabela `cidades`).
Para o JOIN use **dois aliases diferentes** da mesma tabela:

```sql
CREATE TABLE cidades(id INT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(50) NOT NULL);
CREATE TABLE voos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    origem_id INT NOT NULL,
    destino_id INT NOT NULL,
    FOREIGN KEY (origem_id)  REFERENCES cidades(id),
    FOREIGN KEY (destino_id) REFERENCES cidades(id)
);
SELECT v.id, o.nome AS origem, d.nome AS destino
FROM voos v
JOIN cidades o ON o.id = v.origem_id
JOIN cidades d ON d.id = v.destino_id;
```

**Regras de integridade, restrições e valores padrão:**

```sql
-- Comportamento ao apagar/alterar o pai (padrão = bloquear, erro 1451)
FOREIGN KEY (pai_id) REFERENCES pais(id) ON DELETE RESTRICT              -- bloqueia (é o padrão)
FOREIGN KEY (pai_id) REFERENCES pais(id) ON DELETE CASCADE               -- apagar o pai apaga os filhos
FOREIGN KEY (pai_id) REFERENCES pais(id) ON DELETE SET NULL              -- precisa de coluna pai_id INT NULL
FOREIGN KEY (pai_id) REFERENCES pais(id) ON UPDATE CASCADE               -- mudou o id do pai, acompanha

-- Outras restrições na coluna
email VARCHAR(80) UNIQUE,                       -- não repete
quantidade INT NOT NULL DEFAULT 0,              -- valor padrão
ativo BOOLEAN NOT NULL DEFAULT TRUE,
data_cadastro DATE DEFAULT (CURRENT_DATE),      -- MySQL 8: expressão entre parênteses
preco DECIMAL(10,2) NOT NULL CHECK (preco >= 0) -- CHECK vale no MySQL 8.0.16 ou superior
```

| Quero que... | Use | Efeito na tela |
|---|---|---|
| apagar pai com filhos dê erro | padrão (sem `ON DELETE`) | `excluir` devolve `false`, mostrar mensagem |
| apagar pai apague os filhos | `ON DELETE CASCADE` | cuidado: apaga sem avisar; confirmar antes |
| nome não repetir | `UNIQUE` | inserir repetido dá `Duplicate entry` (a tela deve avisar) |

Alterar tabela depois de criada (se esquecer algo):

```sql
ALTER TABLE filhos ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE filhos ADD COLUMN data_cadastro DATE;
ALTER TABLE filhos MODIFY nome VARCHAR(100) NOT NULL;
ALTER TABLE filhos DROP COLUMN ativo;
```

### 3.4 Consultas que a prova pode pedir

Todas usam as tabelas `pais`/`filhos` do script acima.

```sql
-- JOIN: filho com o nome do pai
SELECT f.nome, f.preco, p.nome AS pai
FROM filhos f INNER JOIN pais p ON p.id = f.pai_id;

-- LEFT JOIN: todos os pais, mesmo sem filhos (quantidade 0)
SELECT p.nome, COUNT(f.id) AS qtd_filhos
FROM pais p LEFT JOIN filhos f ON f.pai_id = p.id
GROUP BY p.id, p.nome;

-- LIKE (contém), BETWEEN, IN
SELECT * FROM filhos WHERE nome LIKE '%ca%';
SELECT * FROM filhos WHERE nome LIKE 'M%';              -- começa com M
SELECT * FROM filhos WHERE preco BETWEEN 10 AND 60;
SELECT * FROM filhos WHERE pai_id IN (1, 3);

-- ORDER BY e LIMIT (top 3 mais caros)
SELECT * FROM filhos ORDER BY preco DESC, nome ASC;
SELECT * FROM filhos ORDER BY preco DESC LIMIT 3;

-- Valor de cada linha e valor total em estoque (preco * quantidade)
SELECT nome, preco, quantidade, preco * quantidade AS total FROM filhos;
SELECT SUM(preco * quantidade) AS total_estoque FROM filhos;

-- Agregações
SELECT COUNT(*) AS qtd, SUM(quantidade) AS soma_qtd, AVG(preco) AS media,
       MAX(preco) AS maior, MIN(preco) AS menor
FROM filhos;

-- GROUP BY + HAVING (só grupos com mais de 1 filho)
SELECT p.nome AS pai, COUNT(*) AS qtd, SUM(f.preco * f.quantidade) AS total
FROM filhos f JOIN pais p ON p.id = f.pai_id
GROUP BY p.id, p.nome
HAVING COUNT(*) > 1
ORDER BY total DESC;

-- Subquery: acima da média de preço
SELECT nome, preco FROM filhos WHERE preco > (SELECT AVG(preco) FROM filhos);

-- Pais que não têm nenhum filho
SELECT * FROM pais WHERE id NOT IN (SELECT pai_id FROM filhos);

-- UPDATE e DELETE (SEM WHERE altera/apaga TUDO)
UPDATE filhos SET preco = 65.00, quantidade = 30 WHERE id = 1;
UPDATE filhos SET preco = preco * 1.10 WHERE pai_id = 2;      -- reajuste de 10% do pai 2
DELETE FROM filhos WHERE id = 5;
```

Em `GROUP BY`, no `SELECT` só entram colunas do `GROUP BY` ou funções de agregação (`COUNT`, `SUM`...). Misturar dá
erro por causa do modo `ONLY_FULL_GROUP_BY` do MySQL 8. `WHERE` filtra **linhas** antes de agrupar; `HAVING` filtra **grupos** depois.

---

## 4. MySQL Workbench no laboratório

### 4.1 Criar/abrir a conexão

1. Abra o **MySQL Workbench**. Na tela *Home*, em *MySQL Connections*, se já existir um cartão (ex.: "Local instance MySQL80"), **clique duas vezes** nele.
2. Se não existir: botão **+** ao lado de *MySQL Connections* > *Setup New Connection*:

| Campo | Valor |
|---|---|
| Connection Name | qualquer (ex.: `laboratorio`) |
| Hostname | `127.0.0.1` (ou `localhost`) |
| Port | `3306` |
| Username | `root` |
| Password | botão *Store in Vault...* e digitar `laboratorio` (ou digitar na hora de conectar) |

3. Clique em **Test Connection**. "Successfully made the MySQL connection" = OK; depois **OK** e abra a conexão.
4. Se pedir senha ao abrir: `laboratorio`.

### 4.2 Abrir e executar o script

1. **File > Open SQL Script** (`Ctrl+Shift+O`), escolha o `.sql`; ou use a aba de consulta vazia e cole o texto.
2. **Raio (Execute the selected portion of the script or everything if there is no selection)** na barra: executa **tudo** (`Ctrl+Shift+Enter`).
3. **`Ctrl+Enter`** executa **só a instrução onde o cursor está** (ótimo para ir conferindo `SELECT`s). Selecionar um trecho e usar o raio executa só o trecho.
4. Veja o resultado nas abas inferiores (*Result Grid* e *Output*). Linha vermelha em *Output* = erro: leia a mensagem.

### 4.3 Conferir que o banco existe

```sql
SHOW DATABASES;            -- meubanco deve aparecer
USE meubanco;              -- sem USE: "No database selected"
SHOW TABLES;
DESC filhos;
SELECT * FROM filhos;
SELECT USER(), DATABASE(); -- quem sou eu e em qual banco estou
SHOW VARIABLES LIKE 'port';
```

- **O schema não aparece no painel SCHEMAS (esquerda)?** Clique no ícone de **setas circulares (Refresh)** ao lado do título SCHEMAS. Ele só mostra bancos criados depois se atualizar.
- Para o Workbench usar um banco por padrão: duplo clique no nome do schema (fica em **negrito**) ou `USE`.

### 4.4 Safe Updates (Error Code 1175)

Mensagem: `Error Code: 1175. You are using safe update mode and you tried to update a table without a WHERE that uses a KEY column`.
Acontece em `UPDATE`/`DELETE` sem `WHERE` ou com `WHERE` numa coluna que não é chave.

- Rápido, só nesta sessão: `SET SQL_SAFE_UPDATES = 0;` (execute antes do `UPDATE`/`DELETE`).
- Permanente: **Edit > Preferences > SQL Editor** > desmarcar **Safe Updates (rejects UPDATEs and DELETEs with no restrictions)** > OK > **reconectar** (feche e abra a aba de consulta / reabra a conexão).
- Melhor ainda: use `WHERE id = ...` (chave primária) e nem aparece.

### 4.5 Workbench × MySQL Command Line Client

| | MySQL Workbench | MySQL Command Line Client |
|---|---|---|
| O que é | interface gráfica | terminal preto, pede a senha ao abrir |
| Executar script | abrir arquivo e raio | `source C:/pasta/script.sql` (use `/`, sem aspas) |
| Fim de comando | `;` separa; `Ctrl+Enter` executa | `;` obrigatório, senão fica em `->` |
| Ver resultado | grade | texto |

As aulas (slides) usam o **Command Line Client**; no laboratório pode haver os dois. O banco é o mesmo: o que um criar o outro vê.

### 4.6 Checklist "a conexão do Java vai funcionar?"

- [ ] Serviço **MySQL80** rodando (Workbench conectou = está rodando). Se não conecta: *Serviços do Windows* (`services.msc`) > MySQL80 > Iniciar.
- [ ] Porta: `3306` (conferir com `SHOW VARIABLES LIKE 'port';`).
- [ ] Usuário/senha da `Conexao.java` **iguais** aos do Workbench: `root` / `laboratorio`.
- [ ] **Nome do banco** na URL igual ao `CREATE DATABASE` (`meubanco` nos dois).
- [ ] O script já foi executado (as tabelas existem: `SHOW TABLES;`).
- [ ] `mysql-connector-j-8.1.0.jar` aparece em **Libraries** do projeto (seção 5).

String JDBC usada em todos os projetos do repositório (`Produtos/src/conexao/Conexao.java`):

```
jdbc:mysql://localhost:3306/<banco>?useTimezone=true&serverTimezone=UTC     usuário: root     senha: laboratorio
```

**O que foi de fato testado e o que não foi (honestidade):** o código-modelo deste guia foi compilado e executado contra um
**MySQL 8.0 em Docker**, com o **mesmo driver 8.1.0**, a mesma URL e as mesmas credenciais (`root`/`laboratorio`).
O SQL e os DAOs/Beans foram **executados** de verdade (inserir, buscar, editar trocando a FK, filtros, erro 1451, transação, login, matrícula); as **telas foram só compiladas** (com os componentes declarados), não clicadas. **Não** foi testado no computador do laboratório: versão exata do MySQL de lá, se o serviço está ligado, senha, o aspecto do
Workbench instalado lá e os atalhos do NetBeans da versão de lá. Isso você só confirma **lá**, em 2 minutos, com a lista acima.
Os nomes de menus do Workbench/NetBeans citados aqui podem variar um pouco entre versões.

---

## 5. NetBeans passo a passo

> Referência: `java_swing_sql/Produtos` (telas com `.form` + `.java` feitas no GUI Builder, nomes `txt_`/`cmb_`/`btn_`/`tbl_`/`lbl_`, handlers com `//GEN-FIRST`),
> além de `ProjetoAulaBD` e `ProjetoBDescola`. Os métodos de evento deste guia servem direto: é só colar dentro do método que o NetBeans criou.

### 5.1 Projeto e pacotes

1. **File > New Project > Java with Ant > Java Application** > Next. Nome do projeto (ex.: `Faculdade`). **Desmarque** *Create Main Class* se quiser usar `view.Menu` como principal (ou deixe: não atrapalha).
2. No painel *Projects*: botão direito em **Source Packages > New > Java Package** e crie `conexao`, `Beans`, `DAO`, `view` (um por vez; letras maiúsculas como no repositório).
3. Dentro de cada pacote: botão direito > **New > Java Class** (Conexao, Pai, Filho, PaiDAO, FilhoDAO) ou **New > JFrame Form** (telas, no pacote `view`).

### 5.2 Driver (o jar do MySQL)

1. Copie `mysql-connector-j-8.1.0.jar` (existe em [`../libs/`](../libs/) e em `Produtos/drivers/`) para uma pasta `drivers` dentro do projeto (botão direito no projeto > *Open in System* / abrir a pasta pelo Explorer) — ou deixe na pasta onde estiver.
2. Botão direito em **Libraries > Add JAR/Folder...**, escolha o jar > *Open* (marque *Relative Path* se perguntar).
   Alternativa: *Properties > Libraries > Compile > Add JAR/Folder*.
3. Confira: o jar aparece em *Libraries*. No repositório isso fica em `nbproject/project.properties` (`file.reference.mysql-connector-j-8.1.0.jar=drivers\\mysql-connector-j-8.1.0.jar`).
4. Sem isso: `No suitable driver found for jdbc:mysql://...` (seção 9).

### 5.3 JFrame Form e GUI Builder

1. **New > JFrame Form**. Abra na aba **Design** (alternar *Source/Design* no topo).
2. Arraste da **Palette** (à direita): *Label*, *Text Field*, *Combo Box*, *Button*, *Radio Button*, *Check Box*, *Table*, *Menu Bar*...
3. **Renomear variável:** botão direito no componente > **Change Variable Name...** > `txt_nome`, `cmb_pai`, `btn_salvar`, `tbl_filhos`, `rdo_m`, `chk_ativo`...
   Faça **antes** de ligar eventos, senão o método nasce com o nome antigo (`jButton1ActionPerformed`).
4. **Texto do componente:** duplo clique ou *Properties > text*. Para o *Text Field* apague o texto padrão `jTextField1`.
5. **Ligar evento:** botão direito no componente > **Events > Action > actionPerformed** (cria o método e abre o código), ou **duplo clique no botão**. Para o clique do mouse na tabela: **Events > Mouse > mouseClicked**; para filtrar enquanto digita: **Events > Key > keyReleased**.
6. **defaultCloseOperation:** selecione o JFrame (clique no fundo do formulário ou nó *JFrame* no Navigator) > *Properties* > **defaultCloseOperation = DISPOSE** (nas telas abertas pelo menu). Só o **Menu principal** pode ficar `EXIT`.
7. **Mudar o tamanho/posição:** `setLocationRelativeTo(null);` no construtor, depois de `initComponents();`, centraliza a janela.

### 5.4 Componentes e como ler/escrever neles

| Componente | Nome sugerido | Ler | Escrever / limpar |
|---|---|---|---|
| `JTextField` | `txt_nome` | `txt_nome.getText()` | `txt_nome.setText("")` |
| `JPasswordField` | `txt_senha` | `new String(txt_senha.getPassword())` | `setText("")` |
| `JRadioButton` | `rdo_m`, `rdo_f` | `rdo_m.isSelected()` | `rdo_m.setSelected(true)` |
| `ButtonGroup` | `grp_sexo` | — | `grp_sexo.clearSelection()` |
| `JCheckBox` | `chk_ativo` | `chk_ativo.isSelected()` | `chk_ativo.setSelected(false)` |
| `JComboBox<String>` | `cmb_tipo` | `cmb_tipo.getSelectedItem().toString()` | `cmb_tipo.setSelectedItem("X")` / `setSelectedIndex(0)` |
| `JComboBox<Pai>` | `cmb_pai` | `(Pai) cmb_pai.getSelectedItem()` | `cmb_pai.addItem(p)`, `removeAllItems()` |
| `JTable` | `tbl_filhos` | `tbl_filhos.getSelectedRow()`, `getValueAt(linha, coluna)` | `DefaultTableModel` (`addRow`, `setRowCount(0)`) |
| `JLabel` | `lbl_total` | `lbl_total.getText()` | `lbl_total.setText("...")` |

**RadioButton + ButtonGroup:** arraste um **Button Group** (Palette > *Swing Controls*) para o formulário (aparece em *Other Components* no Navigator, nomeie `grp_sexo`).
Selecione **cada** rádio > *Properties* > **buttonGroup** > escolha `grp_sexo`. Sem o grupo, os dois ficam marcados ao mesmo tempo.
(Em `ProjetoAulaBD` o grupo se chama `btn_grp_sexo`, com `rdo_masculino`/`rdo_feminino`.)

**JComboBox de texto fixo:** *Properties > model* > `...` > digite os itens (um por linha). Mantém `JComboBox<String>`.
**JComboBox de objetos do banco:** *Properties > model* > **apague todos os itens**, aba **Code** > *Type Parameters* = `<Pai>` (e importe `Beans.Pai`), e preencha por código (seção 7). Se ficar `JComboBox<String>`, `addItem(pai)` não compila.

**JTable com DefaultTableModel:** arraste *Table* (já vem dentro de um *Scroll Pane*). *Properties > model* > `...` abre o editor:
aba *Columns* defina número de colunas e **Title** de cada (`ID`, `Nome`, `Preço`...); desmarque **Editable** para cada coluna (só leitura). Apague as linhas de exemplo (*Rows* = 0).
No código: `DefaultTableModel modelo = (DefaultTableModel) tbl_filhos.getModel();`.

**Menu:** arraste **Menu Bar** (Palette > *Swing Menus*) para o formulário. Clique em `File` e renomeie para `Cadastros`. Botão direito no menu > **Add From Palette > Menu Item**; renomeie a variável (`item_cad_filho`) e o texto. Duplo clique no item (ou *Events > Action > actionPerformed*) abre o método.

### 5.5 Classe principal, executar e compilar

1. **Botão direito no projeto > Properties > Run > Main Class > Browse...** > escolha `view.Menu` (ou `produtos.Produtos`). *(Equivalente: Run > Set Project Configuration > Customize > Run.)*
2. **F6** = executa o projeto (usa a classe principal). **Shift+F6** = executa só o arquivo aberto (cada tela tem `main`, útil para testar uma tela sozinha).
3. **Shift+F11** = *Clean and Build* (apaga e compila tudo; use se aparecerem erros estranhos de classe antiga).
4. `Ctrl+Shift+I` = *Fix Imports* (resolve `cannot find symbol` de falta de `import`). `Alt+Shift+F` = formatar. `Alt+Insert` = gerar getters/setters/`equals`/construtor.
5. A saída do `System.out.println` (mensagem de conexão, erros de SQL) aparece na janela **Output** embaixo.
6. A pasta `nbproject/private/` guarda configurações locais do seu computador (ex.: último arquivo aberto); não precisa mexer nela.

---

## 6. Código-modelo genérico: Conexao, Beans e DAOs

Tudo abaixo é a versão com nomes genéricos dos arquivos reais em `java_swing_sql/Produtos/src/`
(`conexao/Conexao.java`, `Beans/Categoria.java`, `Beans/Produto.java`, `DAO/CategoriaDAO.java`, `DAO/ProdutoDAO.java`).
Mapa: `Pai` = `Categoria`, `Filho` = `Produto`, `pais` = `categorias`, `filhos` = `produtos`, `pai_id` = `categoria_id`.

### 6.1 Conexao

```java
// ARQUIVO: conexao/Conexao.java
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    public Connection getConexao(){
        Connection conn;
        try {
            conn = DriverManager.getConnection(
             "jdbc:mysql://localhost:3306/meubanco?useTimezone=true&serverTimezone=UTC","root", "laboratorio"
            );
            System.out.println("Conexao realizada com sucesso!");
            return conn;
        }catch(Exception e){
            System.out.println("Erro ao conectar no DB: "+e.getMessage());
            return null;
        }
    }
}
```

Troque **só** o nome do banco (`meubanco`). Se `getConexao()` falhar devolve `null` e o erro aparece no *Output*; os DAOs
darão `NullPointerException` depois (seção 9).

### 6.2 Bean Pai (com `toString` e `equals`)

```java
// ARQUIVO: Beans/Pai.java
package Beans;

public class Pai {
    private int id;
    private String nome;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    // o JComboBox mostra o que o toString() devolve
    @Override
    public String toString() {
        return this.id + " - " + this.nome;
    }

    // o setSelectedItem() do JComboBox usa equals() para achar o item
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.id == ((Pai) obj).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
```

**Por que `toString`:** sem ele o combo mostra `Beans.Pai@1b6d3586` (endereço do objeto).
**Por que `equals`:** `cmb_pai.setSelectedItem(filho.getPai())` compara com `equals`. O `equals` padrão compara a **referência**;
o `Pai` que veio do JOIN do filho é **outro objeto** (mesmo id, outra instância), então o combo não selecionaria nada.
Comparando pelo `id` funciona. `hashCode` acompanha o `equals` (`Alt+Insert > equals() and hashCode()` gera os dois).

### 6.3 Bean Filho (FK vira objeto)

```java
// ARQUIVO: Beans/Filho.java
package Beans;

public class Filho {
    private int id;
    private String nome;
    private double preco;
    private int quantidade;
    private Pai pai;            // a FK pai_id vira um OBJETO Pai, não um int

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public Pai getPai() { return pai; }
    public void setPai(Pai pai) { this.pai = pai; }

    // campo calculado (não existe na tabela): valor total em estoque
    public double getTotal() { return preco * quantidade; }
}
```

### 6.4 PaiDAO

```java
// ARQUIVO: DAO/PaiDAO.java
package DAO;

import Beans.Pai;
import conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiDAO {
    Conexao conexao;
    Connection conn;

    public PaiDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }

    public void inserir(Pai pai){
        String sql = "INSERT INTO pais(nome) VALUES (?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, pai.getNome());
            stmt.execute();
        }catch(SQLException e){
            System.out.println("Erro ao inserir pai: " + e.getMessage());
        }
    }

    public Pai getPai(int id){
        String sql = "SELECT * FROM pais WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(!rs.first()){
                return null;            // não achou
            }
            Pai p = new Pai();
            p.setId(id);
            p.setNome(rs.getString("nome"));
            return p;
        }catch(SQLException ex){
            System.out.println("Erro ao consultar pai: " + ex.getMessage());
            return null;
        }
    }

    // nome vazio ("") traz todos; senão filtra com LIKE
    public List<Pai> getPais(String nome){
        String sql = "SELECT * FROM pais WHERE nome LIKE ? ORDER BY nome";
        List<Pai> lista = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Pai p = new Pai();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                lista.add(p);
            }
        }catch(SQLException ex){
            System.out.println("Erro ao listar pais: " + ex.getMessage());
        }
        return lista;
    }

    public List<Pai> getPais(){
        return getPais("");
    }

    public void editar(Pai pai){
        try{
            String sql = "UPDATE pais SET nome=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pai.getNome());
            stmt.setInt(2, pai.getId());       // o id é o ÚLTIMO ?
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao atualizar pai: " + ex.getMessage());
        }
    }

    // devolve false se não conseguiu (ex.: ainda existem filhos desse pai -> erro de FK)
    public boolean excluir(int id){
        try{
            String sql = "DELETE FROM pais WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        }catch(SQLException ex){
            System.out.println("Erro ao excluir pai: " + ex.getMessage());
            return false;
        }
    }
}
```

### 6.5 FilhoDAO (JOIN + LIKE + filtros)

```java
// ARQUIVO: DAO/FilhoDAO.java
package DAO;

import Beans.Filho;
import Beans.Pai;
import conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilhoDAO {
    Conexao conexao;
    Connection conn;

    // SELECT base usado em getFilho e getFilhos: o JOIN já traz o nome do pai
    private static final String SELECT_BASE =
        "SELECT f.id, f.nome, f.preco, f.quantidade, f.pai_id, p.nome AS pai_nome "
        + "FROM filhos f INNER JOIN pais p ON p.id = f.pai_id ";

    public FilhoDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }

    public void inserir(Filho filho){
        String sql = "INSERT INTO filhos(nome, preco, quantidade, pai_id) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, filho.getNome());
            stmt.setDouble(2, filho.getPreco());
            stmt.setInt(3, filho.getQuantidade());
            stmt.setInt(4, filho.getPai().getId());      // id do objeto Pai, vai para a FK
            stmt.execute();
        }catch(SQLException e){
            System.out.println("Erro ao inserir filho: " + e.getMessage());
        }
    }

    // transforma a linha atual do ResultSet em um Filho (com o Pai dentro)
    private Filho montar(ResultSet rs) throws SQLException {
        Pai pai = new Pai();
        pai.setId(rs.getInt("pai_id"));
        pai.setNome(rs.getString("pai_nome"));
        Filho f = new Filho();
        f.setId(rs.getInt("id"));
        f.setNome(rs.getString("nome"));
        f.setPreco(rs.getDouble("preco"));
        f.setQuantidade(rs.getInt("quantidade"));
        f.setPai(pai);
        return f;
    }

    public Filho getFilho(int id){
        String sql = SELECT_BASE + "WHERE f.id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                return null;
            }
            return montar(rs);
        }catch(SQLException ex){
            System.out.println("Erro ao consultar filho: " + ex.getMessage());
            return null;
        }
    }

    // Busca por nome E/OU nome do pai. Campo vazio ("") não filtra nada.
    public List<Filho> getFilhos(String nome, String paiNome){
        String sql = SELECT_BASE + "WHERE f.nome LIKE ? AND p.nome LIKE ? ORDER BY f.nome";
        List<Filho> lista = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            stmt.setString(2, "%" + paiNome + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lista.add(montar(rs));
            }
        }catch(SQLException ex){
            System.out.println("Erro ao listar filhos: " + ex.getMessage());
        }
        return lista;
    }

    public List<Filho> getFilhos(){
        return getFilhos("", "");
    }

    // Filtros combinados. paiId <= 0 = qualquer pai; min/max null = sem limite;
    // ordem: "preco" ordena por preço, qualquer outra coisa ordena por nome.
    public List<Filho> getFilhos(String nome, int paiId, Double min, Double max, boolean soEstoque, String ordem){
        StringBuilder sql = new StringBuilder(SELECT_BASE + "WHERE f.nome LIKE ? ");
        List<Object> params = new ArrayList<>();
        params.add("%" + nome + "%");
        if(paiId > 0){
            sql.append("AND f.pai_id = ? ");
            params.add(paiId);
        }
        if(min != null){
            sql.append("AND f.preco >= ? ");
            params.add(min);
        }
        if(max != null){
            sql.append("AND f.preco <= ? ");
            params.add(max);
        }
        if(soEstoque){
            sql.append("AND f.quantidade > 0 ");
        }
        sql.append("preco".equals(ordem) ? "ORDER BY f.preco" : "ORDER BY f.nome");
        List<Filho> lista = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            for(int i = 0; i < params.size(); i++){
                stmt.setObject(i + 1, params.get(i));     // índices começam em 1
            }
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lista.add(montar(rs));
            }
        }catch(SQLException ex){
            System.out.println("Erro ao filtrar filhos: " + ex.getMessage());
        }
        return lista;
    }

    // Um único campo de busca: procura no nome do filho OU no nome do pai
    public List<Filho> buscar(String termo){
        String sql = SELECT_BASE + "WHERE f.nome LIKE ? OR p.nome LIKE ? ORDER BY f.nome";
        List<Filho> lista = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + termo + "%");
            stmt.setString(2, "%" + termo + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lista.add(montar(rs));
            }
        }catch(SQLException ex){
            System.out.println("Erro ao buscar filhos: " + ex.getMessage());
        }
        return lista;
    }

    // inclui pai_id: é assim que o pai do filho é trocado
    public void editar(Filho filho){
        try{
            String sql = "UPDATE filhos SET nome=?, preco=?, quantidade=?, pai_id=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, filho.getNome());
            stmt.setDouble(2, filho.getPreco());
            stmt.setInt(3, filho.getQuantidade());
            stmt.setInt(4, filho.getPai().getId());
            stmt.setInt(5, filho.getId());                // id por último
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao atualizar filho: " + ex.getMessage());
        }
    }

    public boolean excluir(int id){
        try{
            String sql = "DELETE FROM filhos WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        }catch(SQLException ex){
            System.out.println("Erro ao excluir filho: " + ex.getMessage());
            return false;
        }
    }
}
```

Diferenças deliberadas em relação ao repo: no `Produtos/src/DAO/ProdutoDAO.java` o `excluir` é `void` e o alias das
tabelas é `p`/`c` (produto/categoria); aqui `f`/`p`. O molde acima ainda acrescenta o `getFilhos` com filtros combinados e o `buscar`
(um campo, nome **ou** pai), que o repo não tem.

### 6.6 As regras do `PreparedStatement` e do `ResultSet`

| Regra | Exemplo |
|---|---|
| Cada `?` é um parâmetro; numerados **a partir de 1**, na ordem em que aparecem no SQL | `VALUES (?,?,?,?)` → `set...(1)` ... `set...(4)` |
| O `setX` bate com o tipo da coluna | `setString`, `setInt`, `setDouble`, `setDate`, `setBoolean`, `setBigDecimal`, `setObject` |
| `stmt.execute()` / `executeUpdate()` para `INSERT`/`UPDATE`/`DELETE` | `executeUpdate()` devolve **quantas linhas** foram afetadas |
| `stmt.executeQuery()` para `SELECT` | devolve `ResultSet` |
| `rs.next()` vai para a próxima linha; `false` quando acabou | `while(rs.next()){ ... }` |
| `rs.first()` vai para a primeira linha; precisa de `TYPE_SCROLL_INSENSITIVE` | consulta por id (como no `PaiDAO.getPai`) |
| `rs.getX("nome_da_coluna")` usa o nome **do SELECT** (o alias, se houver) | `rs.getString("pai_nome")` por causa do `AS pai_nome` |
| `LIKE` com parâmetro: o `%` entra **no valor**, não no SQL | `stmt.setString(1, "%" + nome + "%")` |
| Campo vazio com `LIKE` | `"%" + "" + "%"` = `%%` = traz tudo |

**Por que `PreparedStatement` e não concatenar:** `"... WHERE nome = '" + txt + "'"` quebra com aspas no texto e permite **SQL Injection**
(`' OR '1'='1`). Com `?` o driver trata tipos e aspas.

**Por que JOIN e não um DAO dentro do loop:** o jeito "preguiçoso" é, para cada linha do `ResultSet`, chamar `new PaiDAO().getPai(...)`.
Cada `new XxxDAO()` **abre uma conexão nova** (o construtor chama `Conexao.getConexao()`): se o DAO for criado dentro do loop, 100 linhas = 100 conexões; mesmo criado fora do loop são 100 consultas extras.
Com `INNER JOIN` o banco já devolve o nome do pai na mesma consulta. (`ProjetoBDescola/src/DAO/DisciplinaDAO.java` usa o jeito do loop, com o `ProfessorDAO` criado antes do `while`; funciona, só é mais lento.)

**Por que `getFilho` usa `rs.next()` no repo real e `rs.first()` no `PaiDAO`:** ambos servem para "pegar uma linha". `rs.first()` exige o `ResultSet` rolável
(`TYPE_SCROLL_INSENSITIVE`), `rs.next()` não. Pode usar `next()` sempre.

---

## 7. Telas modelo

Em cada tela: **(1)** monte o visual no GUI Builder com os **nomes** da tabela de componentes, **(2)** cole os métodos
**dentro da classe** (o construtor e os métodos de evento já existem: substitua o corpo), **(3)** `Ctrl+Shift+I` para os `import`.
Referência real (GUI Builder, mesma lógica): `java_swing_sql/Produtos/src/view/`
(`FormCadastroProduto.java`, `FormConsultaProduto.java`, `RelatorioProdutos.java`, `FormCategorias.java`, `MenuPrincipal.java`).

Imports que as telas usam:

```java
import Beans.Filho;
import Beans.Pai;
import DAO.FilhoDAO;
import DAO.PaiDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
```

### 7.1 Cadastro com combo preenchido do banco

Equivalente real: `Produtos/src/view/FormCadastroProduto.java`.

| Componente | Variável | Observação |
|---|---|---|
| JTextField | `txt_nome` | |
| JTextField | `txt_preco` | |
| JTextField | `txt_quantidade` | |
| JComboBox | `cmb_pai` | *model*: apagar itens; *Type Parameters* `<Pai>` |
| JButton | `btn_salvar` | evento `actionPerformed` |

```java
// TELA: FormCadastroFilho
public FormCadastroFilho() {
    initComponents();
    preencherComboPais();          // depois do initComponents()
}

// busca os pais no banco e joga no combo (objetos Pai, não Strings)
public void preencherComboPais(){
    PaiDAO paiDAO = new PaiDAO();
    List<Pai> lista = paiDAO.getPais();
    cmb_pai.removeAllItems();       // tira "Item 1, Item 2..."
    for(Pai p : lista){
        cmb_pai.addItem(p);
    }
}

private void limparFormulario(){
    txt_nome.setText("");
    txt_preco.setText("");
    txt_quantidade.setText("");
    if(cmb_pai.getItemCount() > 0){
        cmb_pai.setSelectedIndex(0);
    }
    txt_nome.requestFocus();
}

private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {
    Pai pai = (Pai) cmb_pai.getSelectedItem();
    if(pai == null){
        JOptionPane.showMessageDialog(this, "Cadastre um pai antes");
        return;
    }
    if(txt_nome.getText().trim().isEmpty()){
        JOptionPane.showMessageDialog(this, "Informe o nome");
        return;
    }
    Filho f = new Filho();
    f.setNome(txt_nome.getText().trim());
    try{
        // aceita 10,50 e 10.50
        f.setPreco(Double.parseDouble(txt_preco.getText().trim().replace(",", ".")));
        f.setQuantidade(Integer.parseInt(txt_quantidade.getText().trim()));
    }catch(NumberFormatException ex){
        JOptionPane.showMessageDialog(this, "Preco e quantidade devem ser numeros");
        return;
    }
    f.setPai(pai);

    FilhoDAO filhoDAO = new FilhoDAO();
    filhoDAO.inserir(f);
    JOptionPane.showMessageDialog(this, "Cadastrado com sucesso");
    limparFormulario();
}
```

Se quiser o botão **Limpar**, ligue `btn_limpar` a `limparFormulario();`.

### 7.2 Consulta por ID, editar (trocando a FK) e excluir

Equivalente real: `Produtos/src/view/FormConsultaProduto.java`.

| Componente | Variável | Observação |
|---|---|---|
| JTextField | `txt_id` | id a consultar |
| JButton | `btn_consultar` | |
| JTextField | `txt_id2` | id carregado; *Properties > enabled* desmarcado |
| JTextField | `txt_nome`, `txt_preco`, `txt_quantidade` | |
| JComboBox | `cmb_pai` | `<Pai>` |
| JButton | `btn_atualizar`, `btn_excluir` | |

```java
// TELA: FormConsultaFilho
public FormConsultaFilho() {
    initComponents();
    preencherComboPais();
}

public void preencherComboPais(){
    PaiDAO paiDAO = new PaiDAO();
    List<Pai> lista = paiDAO.getPais();
    cmb_pai.removeAllItems();
    for(Pai p : lista){
        cmb_pai.addItem(p);
    }
}

private void limparFormulario(){
    txt_id.setText("");
    txt_id2.setText("");
    txt_nome.setText("");
    txt_preco.setText("");
    txt_quantidade.setText("");
    if(cmb_pai.getItemCount() > 0){
        cmb_pai.setSelectedIndex(0);
    }
}

private void btn_consultarActionPerformed(java.awt.event.ActionEvent evt) {
    int id;
    try{
        id = Integer.parseInt(txt_id.getText().trim());
    }catch(NumberFormatException ex){
        JOptionPane.showMessageDialog(this, "Digite um ID numerico");
        return;
    }
    FilhoDAO filhoDAO = new FilhoDAO();
    Filho f = filhoDAO.getFilho(id);
    if(f == null){
        limparFormulario();
        JOptionPane.showMessageDialog(this, "Registro nao encontrado");
    }else{
        txt_id2.setText(f.getId() + "");
        txt_nome.setText(f.getNome());
        txt_preco.setText(f.getPreco() + "");
        txt_quantidade.setText(f.getQuantidade() + "");
        // seleciona no combo o pai do filho (funciona por causa do equals do Bean)
        cmb_pai.setSelectedItem(f.getPai());
    }
}

private void btn_atualizarActionPerformed(java.awt.event.ActionEvent evt) {
    if(txt_id2.getText().isEmpty()){
        JOptionPane.showMessageDialog(this, "Consulte um registro primeiro");
        return;
    }
    Pai pai = (Pai) cmb_pai.getSelectedItem();
    Filho f = new Filho();
    f.setId(Integer.parseInt(txt_id2.getText()));
    f.setNome(txt_nome.getText().trim());
    try{
        f.setPreco(Double.parseDouble(txt_preco.getText().trim().replace(",", ".")));
        f.setQuantidade(Integer.parseInt(txt_quantidade.getText().trim()));
    }catch(NumberFormatException ex){
        JOptionPane.showMessageDialog(this, "Preco e quantidade devem ser numeros");
        return;
    }
    f.setPai(pai);              // pai escolhido no combo (pode ter sido trocado)

    FilhoDAO filhoDAO = new FilhoDAO();
    filhoDAO.editar(f);
    JOptionPane.showMessageDialog(this, "Atualizado");
    limparFormulario();
}

private void btn_excluirActionPerformed(java.awt.event.ActionEvent evt) {
    if(txt_id2.getText().isEmpty()){
        JOptionPane.showMessageDialog(this, "Consulte um registro primeiro");
        return;
    }
    int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esse registro?", "Exclusao", JOptionPane.YES_NO_OPTION);
    if(resposta == JOptionPane.YES_OPTION){
        FilhoDAO filhoDAO = new FilhoDAO();
        if(filhoDAO.excluir(Integer.parseInt(txt_id2.getText()))){
            limparFormulario();
        }else{
            JOptionPane.showMessageDialog(this, "Nao foi possivel excluir");
        }
    }
}
```

**Trocar a FK** é só isto: o combo já tem o pai selecionado; ao atualizar, `f.setPai((Pai) cmb_pai.getSelectedItem())` e o `editar` do DAO grava `pai_id=?`.

### 7.3 Relatório com JTable e filtros

Equivalente real (versão mais simples, nome + nome do pai): `Produtos/src/view/RelatorioProdutos.java`.
Padrão das aulas: `ProjetoAulaBD/src/view/RelatorioPessoasComFIltro.java` (método `preeencheTabela()`, rádio `rdo_masculino`/`rdo_feminino`, `txt_nome`).

| Componente | Variável | Observação |
|---|---|---|
| JTextField | `txt_nome` | `LIKE` no nome |
| JComboBox | `cmb_pai` | `<Pai>`; primeiro item "(todos)" com id 0 |
| JTextField | `txt_min`, `txt_max` | faixa de preço |
| JCheckBox | `chk_so_estoque` | "somente com estoque" (`quantidade > 0`) |
| JRadioButton + ButtonGroup | `rdo_ordem_nome`, `rdo_ordem_preco`, `grp_ordem` | ordenar por nome ou preço |
| JButton | `btn_filtrar`, `btn_limpar` | |
| JTable | `tbl_filhos` | colunas: ID, Nome, Preço, Quantidade, Pai, Total |
| JLabel | `lbl_qtd`, `lbl_total` | contagem e soma |

```java
// TELA: FormRelatorioFilhos
public FormRelatorioFilhos() {
    initComponents();
    preencherComboPais();
    rdo_ordem_nome.setSelected(true);
    carregarTabela();               // ao abrir, mostra todos
}

// primeiro item do combo = "(todos)" (id 0 = não filtra por pai)
public void preencherComboPais(){
    cmb_pai.removeAllItems();
    Pai todos = new Pai();
    todos.setId(0);
    todos.setNome("(todos)");
    cmb_pai.addItem(todos);
    for(Pai p : new PaiDAO().getPais()){
        cmb_pai.addItem(p);
    }
}

// "" -> null (sem limite); "12,5" -> 12.5; texto inválido -> NumberFormatException
private Double lerDouble(String texto){
    texto = texto.trim().replace(",", ".");
    if(texto.isEmpty()){
        return null;
    }
    return Double.valueOf(texto);
}

public void carregarTabela(){
    Double min;
    Double max;
    try{
        min = lerDouble(txt_min.getText());
        max = lerDouble(txt_max.getText());
    }catch(NumberFormatException ex){
        JOptionPane.showMessageDialog(this, "Faixa de preco invalida");
        return;
    }
    Pai pai = (Pai) cmb_pai.getSelectedItem();
    int paiId = (pai == null) ? 0 : pai.getId();
    String ordem = rdo_ordem_preco.isSelected() ? "preco" : "nome";

    List<Filho> lista = new FilhoDAO().getFilhos(txt_nome.getText().trim(), paiId, min, max,
            chk_so_estoque.isSelected(), ordem);

    DefaultTableModel modelo = (DefaultTableModel) tbl_filhos.getModel();
    modelo.setRowCount(0);          // LIMPA antes, senão duplica as linhas
    double soma = 0;
    for(Filho f : lista){
        modelo.addRow(new Object[]{
            f.getId(),
            f.getNome(),
            String.format("%.2f", f.getPreco()),
            f.getQuantidade(),
            f.getPai().getNome(),
            String.format("%.2f", f.getTotal())
        });
        soma += f.getTotal();
    }
    lbl_qtd.setText(lista.size() + " registro(s)");
    lbl_total.setText("Total em estoque: " + String.format("%.2f", soma));
}

private void btn_filtrarActionPerformed(java.awt.event.ActionEvent evt) {
    carregarTabela();
}

private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {
    txt_nome.setText("");
    txt_min.setText("");
    txt_max.setText("");
    chk_so_estoque.setSelected(false);
    rdo_ordem_nome.setSelected(true);
    cmb_pai.setSelectedIndex(0);
    carregarTabela();
}

// filtrar enquanto digita: Events > Key > keyReleased do txt_nome
private void txt_nomeKeyReleased(java.awt.event.KeyEvent evt) {
    carregarTabela();
}
```

Notas:

- **Não** ligue `carregarTabela()` ao evento do combo **antes** de ele estar preenchido (o `addItem` dispara `actionPerformed`
  e a tabela pode ainda não existir). Ligue só aos botões, ou depois do construtor.
- Se o combo "todos" não for desejado, use `JComboBox<Pai>` normal e trate `getSelectedItem() == null`.
- Busca por nome **ou** pai com **um só** campo: troque a chamada por `new FilhoDAO().buscar(txt_busca.getText().trim())` (método do DAO na seção 6.5).
- Busca por nome **e** nome do pai (dois campos de texto, como no repo): `getFilhos(txt_nome.getText().trim(), txt_pai.getText().trim())`.

### 7.4 Tela única: CRUD + JTable (clique na linha preenche; salvar decide inserir/atualizar)

Equivalente real: `Produtos/src/view/FormCategorias.java` (aí só com o pai; aqui com o filho e o combo; componentes `txt_id`, `txt_nome`, `btn_salvar`, `btn_novo`, `btn_excluir`, `tbl_categorias`).
Aqui **não há botão "Editar"**: o `txt_id` vazio significa "novo registro" (INSERT); preenchido significa "alterando" (UPDATE).

| Componente | Variável | Observação |
|---|---|---|
| JTextField | `txt_id` | *enabled* desmarcado (só mostra) |
| JTextField | `txt_nome`, `txt_preco`, `txt_quantidade` | |
| JComboBox | `cmb_pai` | `<Pai>` |
| JButton | `btn_salvar`, `btn_novo`, `btn_excluir` | |
| JTable | `tbl_filhos` | colunas ID, Nome, Preço, Quantidade, Pai; *Editable* desmarcado |
| JLabel | `lbl_total` | contagem |

```java
// TELA: FormFilhos
public FormFilhos() {
    initComponents();
    txt_id.setEnabled(false);
    preencherComboPais();
    carregarTabela();
}

public void preencherComboPais(){
    List<Pai> lista = new PaiDAO().getPais();
    cmb_pai.removeAllItems();
    for(Pai p : lista){
        cmb_pai.addItem(p);
    }
}

private void carregarTabela(){
    List<Filho> lista = new FilhoDAO().getFilhos();
    DefaultTableModel tabela = (DefaultTableModel) tbl_filhos.getModel();
    tabela.setRowCount(0);
    for(Filho f : lista){
        Object[] obj = { f.getId(), f.getNome(), f.getPreco(), f.getQuantidade(), f.getPai().getNome() };
        tabela.addRow(obj);
    }
    lbl_total.setText(lista.size() + " registro(s)");
}

private void limparFormulario(){
    txt_id.setText("");
    txt_nome.setText("");
    txt_preco.setText("");
    txt_quantidade.setText("");
    if(cmb_pai.getItemCount() > 0){
        cmb_pai.setSelectedIndex(0);
    }
    tbl_filhos.clearSelection();
    txt_nome.requestFocus();
}

// Events > Mouse > mouseClicked da tabela: linha -> campos (modo edição)
private void tbl_filhosMouseClicked(java.awt.event.MouseEvent evt) {
    int linha = tbl_filhos.getSelectedRow();
    if(linha < 0){
        return;
    }
    int id = Integer.parseInt(tbl_filhos.getValueAt(linha, 0).toString());
    Filho f = new FilhoDAO().getFilho(id);        // busca completo (inclui o Pai) para selecionar o combo
    if(f == null){
        return;
    }
    txt_id.setText(f.getId() + "");
    txt_nome.setText(f.getNome());
    txt_preco.setText(f.getPreco() + "");
    txt_quantidade.setText(f.getQuantidade() + "");
    cmb_pai.setSelectedItem(f.getPai());
    if(evt.getClickCount() == 2){                  // duplo clique
        JOptionPane.showMessageDialog(this, "Registro: " + f.getNome() + " (" + f.getPai().getNome() + ")");
    }
}

private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {
    Pai pai = (Pai) cmb_pai.getSelectedItem();
    if(pai == null || txt_nome.getText().trim().isEmpty()){
        JOptionPane.showMessageDialog(this, "Informe o nome e escolha o pai");
        return;
    }
    Filho f = new Filho();
    f.setNome(txt_nome.getText().trim());
    try{
        f.setPreco(Double.parseDouble(txt_preco.getText().trim().replace(",", ".")));
        f.setQuantidade(Integer.parseInt(txt_quantidade.getText().trim()));
    }catch(NumberFormatException ex){
        JOptionPane.showMessageDialog(this, "Preco e quantidade devem ser numeros");
        return;
    }
    f.setPai(pai);
    FilhoDAO filhoDAO = new FilhoDAO();
    if(txt_id.getText().isEmpty()){
        filhoDAO.inserir(f);
        JOptionPane.showMessageDialog(this, "Cadastrado");
    }else{
        f.setId(Integer.parseInt(txt_id.getText()));
        filhoDAO.editar(f);
        JOptionPane.showMessageDialog(this, "Atualizado");
    }
    carregarTabela();
    limparFormulario();
}

private void btn_novoActionPerformed(java.awt.event.ActionEvent evt) {
    limparFormulario();
}

private void btn_excluirActionPerformed(java.awt.event.ActionEvent evt) {
    if(txt_id.getText().isEmpty()){
        JOptionPane.showMessageDialog(this, "Selecione um registro na tabela");
        return;
    }
    int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esse registro?", "Exclusao", JOptionPane.YES_NO_OPTION);
    if(resposta == JOptionPane.YES_OPTION){
        if(new FilhoDAO().excluir(Integer.parseInt(txt_id.getText()))){
            carregarTabela();
            limparFormulario();
        }else{
            JOptionPane.showMessageDialog(this, "Nao foi possivel excluir");
        }
    }
}
```

O repo usa exatamente `mouseClicked` (`tbl_categoriasMouseClicked` em `FormCategorias.java`). Alternativa que também funciona com o teclado:
`tbl_filhos.getSelectionModel().addListSelectionListener(evt -> { if(!evt.getValueIsAdjusting()){ ... } });`.

### 7.5 Menu principal

Equivalente real: `Produtos/src/view/MenuPrincipal.java` (itens `menu_cadProduto`, `menu_categorias`, `menu_consultaProduto`, `menu_relatorioProdutos`, `menu_fechar`) e `ProjetoBDescola/src/view/MenuPrincipal.java`.

Estrutura sugerida (cada item = um **Menu Item** com variável própria):

| Menu | Item (variável) | Abre |
|---|---|---|
| Cadastros | `item_cad_filho` | `FormCadastroFilho` |
| Cadastros | `item_cad_pai` | tela de cadastro do pai |
| Consultas | `item_cons_filho` | `FormConsultaFilho` |
| Relatórios | `item_rel_filhos` | `FormRelatorioFilhos` |
| Sair | `item_sair` | fecha o sistema |

```java
// TELA: Menu
private void item_cad_filhoActionPerformed(java.awt.event.ActionEvent evt) {
    new FormCadastroFilho().setVisible(true);
}

private void item_cons_filhoActionPerformed(java.awt.event.ActionEvent evt) {
    new FormConsultaFilho().setVisible(true);
}

private void item_rel_filhosActionPerformed(java.awt.event.ActionEvent evt) {
    new FormRelatorioFilhos().setVisible(true);
}

private void item_sairActionPerformed(java.awt.event.ActionEvent evt) {
    int r = JOptionPane.showConfirmDialog(this, "Deseja sair do sistema?", "Sair", JOptionPane.YES_NO_OPTION);
    if(r == JOptionPane.YES_OPTION){
        System.exit(0);
    }
}
```

- O **Menu** é a classe principal (*Run > Main Class*): `defaultCloseOperation = EXIT` só nele.
- Todas as outras telas: **DISPOSE**.
- O evento é o `actionPerformed` do **Menu Item**. O `JMenu` (`menu_cadastros`) é só o rótulo que abre a lista; ligar evento nele é erro comum.

### 7.6 Pequenos recursos que aparecem nas telas

| Pedido | Como (resumo; detalhes na seção 8) |
|---|---|
| Botão **Limpar** | `limparFormulario()` + `requestFocus()` |
| **Confirmação** de exclusão | `JOptionPane.showConfirmDialog(...)` e testar `YES_OPTION` (7.2) |
| **Validação** | vazio: `.trim().isEmpty()`; número: `try/catch NumberFormatException`; combo: `== null` |
| **Formatar preço** | `String.format("%.2f", valor)` (como no repo) ou `NumberFormat.getCurrencyInstance(new Locale("pt","BR"))` |
| **Duplo clique** na linha | `evt.getClickCount() == 2` no `mouseClicked` (7.4) |
| Busca "por nome **ou** categoria" | `FilhoDAO.buscar(termo)` (6.5) |

---

## 8. Catálogo de variações de prova

Cada item: **o que mudar** + trecho pronto. Os trechos usam os nomes do molde (`Pai`, `Filho`, `cmb_pai`...).

### 8.1 Tipos de campo (Bean, DAO e tela)

Para acrescentar um campo novo ao `Filho`, mexa **sempre nos mesmos 6 lugares**:

| # | Onde | O que fazer |
|---|---|---|
| 1 | SQL | coluna nova no `CREATE TABLE` (ou `ALTER TABLE ... ADD COLUMN`) |
| 2 | Bean `Filho` | atributo + getter/setter |
| 3 | `FilhoDAO.SELECT_BASE` | `f.campo` no `SELECT` |
| 4 | `FilhoDAO.montar()` | `f.setCampo(rs.getXxx("campo"))` |
| 5 | `FilhoDAO.inserir()` e `editar()` | coluna no SQL, `?` a mais e o `stmt.setXxx(n, ...)` (conferir a **numeração** e o id por último no `editar`) |
| 6 | Telas | componente + ler (cadastro/atualizar) + mostrar (consultar/tabela) |

```java
// ARQUIVO: DAO/ExemplosTipos.java
package DAO;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExemplosTipos {

    // Gravar: cada tipo com o seu setXxx
    void gravar(PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, 10);                                           // INT
        stmt.setDouble(2, 12.5);                                      // DECIMAL como double
        stmt.setBigDecimal(3, new BigDecimal("12.50"));               // DECIMAL exato (dinheiro)
        stmt.setString(4, "texto");                                   // VARCHAR / sexo 'M' ou 'F'
        stmt.setBoolean(5, true);                                     // BOOLEAN
        stmt.setDate(6, java.sql.Date.valueOf(LocalDate.of(2026, 3, 15)));  // DATE a partir de LocalDate
        stmt.setDate(7, java.sql.Date.valueOf("2026-03-15"));         // DATE a partir de texto yyyy-MM-dd
    }

    // Ler: o getXxx bate com o tipo da coluna
    void ler(ResultSet rs) throws SQLException {
        int quantidade = rs.getInt("quantidade");
        double preco = rs.getDouble("preco");
        BigDecimal exato = rs.getBigDecimal("preco");
        String nome = rs.getString("nome");
        boolean ativo = rs.getBoolean("ativo");
        java.sql.Date data = rs.getDate("data_cadastro");                 // pode ser null se a coluna for NULL
        LocalDate localDate = (data == null) ? null : data.toLocalDate();
        System.out.println(quantidade + preco + exato.toString() + nome + ativo + localDate);
    }

    // Tela digita "15/03/2026"; banco quer java.sql.Date
    public static java.sql.Date textoParaSqlDate(String texto) {
        LocalDate d = LocalDate.parse(texto.trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return java.sql.Date.valueOf(d);          // DateTimeParseException se o texto for inválido
    }

    // Mesma coisa com SimpleDateFormat (jeito antigo, muito usado em aula)
    public static java.sql.Date textoParaSqlDateAntigo(String texto) throws java.text.ParseException {
        java.util.Date utilDate = new SimpleDateFormat("dd/MM/yyyy").parse(texto.trim());
        return new java.sql.Date(utilDate.getTime());
    }

    // Banco -> tela
    public static String dataParaTexto(java.sql.Date data) {
        return data == null ? "" : data.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
```

Tela: rádio M/F, combo de String fixa, checkbox:

```java
// TELA: FormTipos
// Rádio: dois JRadioButton no mesmo ButtonGroup (grp_sexo); guarda 'M'/'F' em VARCHAR(1)
private String lerSexo(){
    if(rdo_m.isSelected()){
        return "M";
    }else if(rdo_f.isSelected()){
        return "F";
    }
    return null;                       // nada marcado: validar
}

private void mostrarSexo(String sexo){
    if("M".equals(sexo)){
        rdo_m.setSelected(true);
    }else if("F".equals(sexo)){
        rdo_f.setSelected(true);
    }else{
        grp_sexo.clearSelection();
    }
}

// Combo de String fixa (JComboBox<String> com itens no model): ler e selecionar
private void exemploCombo(){
    String tipo = cmb_tipo.getSelectedItem().toString();     // ler
    cmb_tipo.setSelectedItem(tipo);                          // mostrar (precisa ser um item existente)
}

// Checkbox <-> BOOLEAN
private void exemploCheck(Filho f){
    boolean ativo = chk_ativo.isSelected();                  // ler (para setBoolean)
    chk_ativo.setSelected(ativo);                            // mostrar (vindo de rs.getBoolean)
}

// Data no formulário (txt_data com dd/MM/yyyy)
private void exemploData(){
    try{
        java.sql.Date data = ExemplosTipos_converter(txt_data.getText());
        System.out.println(data);
    }catch(java.time.format.DateTimeParseException ex){
        JOptionPane.showMessageDialog(this, "Data invalida. Use dd/MM/aaaa");
    }
}

private java.sql.Date ExemplosTipos_converter(String texto){
    return DAO.ExemplosTipos.textoParaSqlDate(texto);
}
```

(Na prova coloque a conversão de data numa classe sua; o nome `ExemplosTipos_converter` acima é só para o trecho compilar.)

**Double ou BigDecimal?** `double` basta para a prova e é o que o repo usa. `BigDecimal` é o correto para dinheiro (sem erro de arredondamento):
`rs.getBigDecimal`, `stmt.setBigDecimal`, atributo `BigDecimal`, e `new BigDecimal(txt_preco.getText().trim().replace(",", "."))`.

### 8.2 Duas FKs: matrícula (aluno + disciplina, com 2 combos)

Banco: tabelas `alunos`, `disciplinas`, `matriculas` da seção 3.3 (padrão do repo: `escola.sql` tem `matricula` com `id_aluno`, `id_professor`, `data_matricula`).
**O que mudar:** o Filho é a **matrícula** (FK para dois pais). Cada pai tem Bean + DAO no molde `Pai`/`PaiDAO`
(Aluno: `Pai` com `Aluno`/`alunos`; Disciplina: `Pai` com `Disciplina`/`disciplinas`).

```java
// ARQUIVO: Beans/Aluno.java
package Beans;

public class Aluno {
    private int id;
    private String nome;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    @Override public String toString() { return this.id + " - " + this.nome; }
    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.id == ((Aluno) obj).id;
    }
    @Override public int hashCode() { return Integer.hashCode(id); }
}
```

```java
// ARQUIVO: Beans/Disciplina.java
package Beans;

public class Disciplina {
    private int id;
    private String nome;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    @Override public String toString() { return this.id + " - " + this.nome; }
    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.id == ((Disciplina) obj).id;
    }
    @Override public int hashCode() { return Integer.hashCode(id); }
}
```

```java
// ARQUIVO: Beans/Matricula.java
package Beans;

import java.time.LocalDate;

public class Matricula {
    private int id;
    private Aluno aluno;
    private Disciplina disciplina;
    private LocalDate data;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}
```

```java
// ARQUIVO: DAO/MatriculaDAO.java
package DAO;

import Beans.Aluno;
import Beans.Disciplina;
import Beans.Matricula;
import conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {
    Conexao conexao;
    Connection conn;

    // 2 JOINs: um para cada pai
    private static final String SELECT_BASE =
        "SELECT m.id, m.data_matricula, a.id AS aluno_id, a.nome AS aluno_nome, "
        + "d.id AS disciplina_id, d.nome AS disciplina_nome "
        + "FROM matriculas m "
        + "INNER JOIN alunos a ON a.id = m.id_aluno "
        + "INNER JOIN disciplinas d ON d.id = m.id_disciplina ";

    public MatriculaDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }

    public boolean inserir(Matricula m){
        String sql = "INSERT INTO matriculas(id_aluno, id_disciplina, data_matricula) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, m.getAluno().getId());
            stmt.setInt(2, m.getDisciplina().getId());
            stmt.setDate(3, java.sql.Date.valueOf(m.getData()));
            stmt.execute();
            return true;
        }catch(SQLException e){
            // inclusive "Duplicate entry" quando o UNIQUE(id_aluno, id_disciplina) é violado
            System.out.println("Erro ao inserir matricula: " + e.getMessage());
            return false;
        }
    }

    private Matricula montar(ResultSet rs) throws SQLException {
        Aluno a = new Aluno();
        a.setId(rs.getInt("aluno_id"));
        a.setNome(rs.getString("aluno_nome"));
        Disciplina d = new Disciplina();
        d.setId(rs.getInt("disciplina_id"));
        d.setNome(rs.getString("disciplina_nome"));
        Matricula m = new Matricula();
        m.setId(rs.getInt("id"));
        m.setAluno(a);
        m.setDisciplina(d);
        java.sql.Date data = rs.getDate("data_matricula");
        m.setData(data == null ? null : data.toLocalDate());
        return m;
    }

    // filtro por nome do aluno (vazio = todas)
    public List<Matricula> getMatriculas(String nomeAluno){
        String sql = SELECT_BASE + "WHERE a.nome LIKE ? ORDER BY m.id";
        List<Matricula> lista = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nomeAluno + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lista.add(montar(rs));
            }
        }catch(SQLException ex){
            System.out.println("Erro ao listar matriculas: " + ex.getMessage());
        }
        return lista;
    }

    // N:N — disciplinas em que um aluno está matriculado
    public List<Disciplina> getDisciplinasDoAluno(int alunoId){
        String sql = "SELECT d.id, d.nome FROM disciplinas d "
                   + "INNER JOIN matriculas m ON m.id_disciplina = d.id "
                   + "WHERE m.id_aluno = ? ORDER BY d.nome";
        List<Disciplina> lista = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, alunoId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Disciplina d = new Disciplina();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                lista.add(d);
            }
        }catch(SQLException ex){
            System.out.println("Erro ao listar disciplinas do aluno: " + ex.getMessage());
        }
        return lista;
    }

    // desvincular (apaga só a linha de ligação; aluno e disciplina continuam)
    public boolean excluir(int alunoId, int disciplinaId){
        String sql = "DELETE FROM matriculas WHERE id_aluno = ? AND id_disciplina = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, alunoId);
            stmt.setInt(2, disciplinaId);
            return stmt.executeUpdate() > 0;
        }catch(SQLException ex){
            System.out.println("Erro ao excluir matricula: " + ex.getMessage());
            return false;
        }
    }
}
```

Tela de matrícula (`cmb_aluno` `<Aluno>`, `cmb_disciplina` `<Disciplina>`, `txt_data`, `btn_salvar`):

```java
// TELA: FormMatricula
public FormMatricula() {
    initComponents();
    preencherCombos();
}

private void preencherCombos(){
    cmb_aluno.removeAllItems();
    for(Aluno a : new AlunoDAO().getAlunos()){
        cmb_aluno.addItem(a);
    }
    cmb_disciplina.removeAllItems();
    for(Disciplina d : new DisciplinaDAO().getDisciplinas()){
        cmb_disciplina.addItem(d);
    }
}

private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {
    Aluno aluno = (Aluno) cmb_aluno.getSelectedItem();
    Disciplina disciplina = (Disciplina) cmb_disciplina.getSelectedItem();
    if(aluno == null || disciplina == null){
        JOptionPane.showMessageDialog(this, "Cadastre aluno e disciplina antes");
        return;
    }
    Matricula m = new Matricula();
    m.setAluno(aluno);
    m.setDisciplina(disciplina);
    try{
        m.setData(java.time.LocalDate.parse(txt_data.getText().trim(),
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }catch(java.time.format.DateTimeParseException ex){
        JOptionPane.showMessageDialog(this, "Data invalida. Use dd/MM/aaaa");
        return;
    }
    if(new MatriculaDAO().inserir(m)){
        JOptionPane.showMessageDialog(this, "Matricula realizada");
        txt_data.setText("");
    }else{
        JOptionPane.showMessageDialog(this, "Nao foi possivel matricular (ja matriculado?)");
    }
}
```

`AlunoDAO.getAlunos()` e `DisciplinaDAO.getDisciplinas()` saem do molde `PaiDAO.getPais()` trocando `Pai`/`pais`.
Para data de hoje sem digitar: `java.time.LocalDate.now()`.

### 8.3 N:N com lista (disciplinas do aluno selecionado)

**O que mudar:** na tela, ao escolher o aluno no combo, mostrar suas disciplinas num `JList<Disciplina>` (`lst_disciplinas`;
*Properties > Code > Type Parameters* `<Disciplina>`). Usa `MatriculaDAO.getDisciplinasDoAluno` do item anterior.

```java
// TELA: FormMatriculaLista
// Events > Action > actionPerformed do cmb_aluno
private void cmb_alunoActionPerformed(java.awt.event.ActionEvent evt) {
    atualizarLista();
}

private void atualizarLista(){
    Aluno aluno = (Aluno) cmb_aluno.getSelectedItem();
    DefaultListModel<Disciplina> modelo = new DefaultListModel<>();
    if(aluno != null){
        for(Disciplina d : new MatriculaDAO().getDisciplinasDoAluno(aluno.getId())){
            modelo.addElement(d);                        // o JList mostra o toString()
        }
    }
    lst_disciplinas.setModel(modelo);
}

// Remover o vínculo da disciplina selecionada na lista
private void btn_desvincularActionPerformed(java.awt.event.ActionEvent evt) {
    Aluno aluno = (Aluno) cmb_aluno.getSelectedItem();
    Disciplina disc = lst_disciplinas.getSelectedValue();   // null se nada selecionado
    if(aluno == null || disc == null){
        JOptionPane.showMessageDialog(this, "Selecione uma disciplina na lista");
        return;
    }
    new MatriculaDAO().excluir(aluno.getId(), disc.getId());
    atualizarLista();
}
```

SQL de relatório N:N (disciplinas por aluno / alunos por disciplina):

```sql
SELECT a.nome AS aluno, d.nome AS disciplina, m.data_matricula
FROM matriculas m
JOIN alunos a ON a.id = m.id_aluno
JOIN disciplinas d ON d.id = m.id_disciplina
ORDER BY a.nome, d.nome;

SELECT d.nome AS disciplina, COUNT(m.id) AS qtd_alunos
FROM disciplinas d LEFT JOIN matriculas m ON m.id_disciplina = d.id
GROUP BY d.id, d.nome;
```

### 8.4 Métodos extras de DAO (total, contagem, estoque, id gerado, excluir pai com filhos)

Um arquivo só com os trechos de SQL "diferentes" que a prova costuma pedir. Em um projeto real, coloque cada método no DAO da sua tabela.

```java
// ARQUIVO: DAO/ExtrasDAO.java
package DAO;

import Beans.Filho;
import conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExtrasDAO {
    Conexao conexao;
    Connection conn;

    public ExtrasDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }

    // Valor total em estoque (SUM de preco * quantidade). COALESCE: 0 se a tabela estiver vazia
    public double getTotalEstoque(){
        String sql = "SELECT COALESCE(SUM(preco * quantidade), 0) AS total FROM filhos";
        try{
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            rs.next();
            return rs.getDouble("total");
        }catch(SQLException ex){
            System.out.println("Erro no total: " + ex.getMessage());
            return 0;
        }
    }

    // Quantos registros existem
    public int contar(){
        try{
            ResultSet rs = conn.prepareStatement("SELECT COUNT(*) AS qtd FROM filhos").executeQuery();
            rs.next();
            return rs.getInt("qtd");
        }catch(SQLException ex){
            System.out.println("Erro ao contar: " + ex.getMessage());
            return 0;
        }
    }

    // COUNT/SUM por pai (GROUP BY). Cada linha: { nomeDoPai, quantidade, totalEmEstoque }
    public List<Object[]> contarPorPai(){
        String sql = "SELECT p.nome, COUNT(f.id) AS qtd, COALESCE(SUM(f.preco * f.quantidade), 0) AS total "
                   + "FROM pais p LEFT JOIN filhos f ON f.pai_id = p.id "
                   + "GROUP BY p.id, p.nome ORDER BY p.nome";
        List<Object[]> linhas = new ArrayList<>();
        try{
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            while(rs.next()){
                linhas.add(new Object[]{ rs.getString("nome"), rs.getInt("qtd"), rs.getDouble("total") });
            }
        }catch(SQLException ex){
            System.out.println("Erro no relatorio por pai: " + ex.getMessage());
        }
        return linhas;
    }

    // Entrada de estoque: soma
    public boolean entrada(int id, int qtd){
        String sql = "UPDATE filhos SET quantidade = quantidade + ? WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qtd);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;      // false se o id não existe
        }catch(SQLException ex){
            System.out.println("Erro na entrada: " + ex.getMessage());
            return false;
        }
    }

    // Baixa de estoque: só baixa se houver saldo (o AND impede ficar negativo)
    public boolean baixa(int id, int qtd){
        String sql = "UPDATE filhos SET quantidade = quantidade - ? WHERE id = ? AND quantidade >= ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qtd);
            stmt.setInt(2, id);
            stmt.setInt(3, qtd);
            return stmt.executeUpdate() > 0;      // 0 linhas = id inexistente OU saldo insuficiente
        }catch(SQLException ex){
            System.out.println("Erro na baixa: " + ex.getMessage());
            return false;
        }
    }

    // Insere e devolve o id que o AUTO_INCREMENT gerou (-1 se falhar)
    public int inserirRetornandoId(Filho filho){
        String sql = "INSERT INTO filhos(nome, preco, quantidade, pai_id) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, filho.getNome());
            stmt.setDouble(2, filho.getPreco());
            stmt.setInt(3, filho.getQuantidade());
            stmt.setInt(4, filho.getPai().getId());
            stmt.executeUpdate();
            ResultSet chaves = stmt.getGeneratedKeys();
            if(chaves.next()){
                return chaves.getInt(1);
            }
            return -1;
        }catch(SQLException ex){
            System.out.println("Erro ao inserir: " + ex.getMessage());
            return -1;
        }
    }

    public int contarFilhosDoPai(int paiId){
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS qtd FROM filhos WHERE pai_id = ?");
            stmt.setInt(1, paiId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("qtd");
        }catch(SQLException ex){
            System.out.println("Erro ao contar filhos do pai: " + ex.getMessage());
            return -1;
        }
    }

    // Excluir o pai E os filhos, numa transação: ou apaga tudo ou não apaga nada
    public boolean excluirPaiComFilhos(int paiId){
        try{
            conn.setAutoCommit(false);
            PreparedStatement s1 = conn.prepareStatement("DELETE FROM filhos WHERE pai_id = ?");
            s1.setInt(1, paiId);
            s1.executeUpdate();
            PreparedStatement s2 = conn.prepareStatement("DELETE FROM pais WHERE id = ?");
            s2.setInt(1, paiId);
            s2.executeUpdate();
            conn.commit();
            return true;
        }catch(SQLException ex){
            System.out.println("Erro ao excluir pai com filhos: " + ex.getMessage());
            try{
                conn.rollback();
            }catch(SQLException ex2){
                System.out.println("Erro no rollback: " + ex2.getMessage());
            }
            return false;
        }finally{
            try{
                conn.setAutoCommit(true);
            }catch(SQLException ex3){
                System.out.println("Erro ao religar autocommit: " + ex3.getMessage());
            }
        }
    }
}
```

Telas que usam esses métodos:

```java
// TELA: FormExtras
// Total calculado e soma do relatório num JLabel (a soma também sai do DAO sem passar pela tabela)
private void mostrarTotais(){
    ExtrasDAO extras = new ExtrasDAO();
    lbl_total.setText("Total em estoque: " + String.format("%.2f", extras.getTotalEstoque()));
    lbl_qtd.setText("Registros: " + extras.contar());           // contagem de registros na tela
}

// Relatório COUNT por pai (JTable com colunas Pai, Quantidade, Total)
private void carregarTabelaPorPai(){
    DefaultTableModel modelo = (DefaultTableModel) tbl_filhos.getModel();
    modelo.setRowCount(0);
    for(Object[] linha : new ExtrasDAO().contarPorPai()){
        modelo.addRow(linha);
    }
}

// Dar baixa / entrada. txt_id = id do filho; txt_movimento = quantidade
private void btn_baixaActionPerformed(java.awt.event.ActionEvent evt) {
    movimentar(false);
}

private void btn_entradaActionPerformed(java.awt.event.ActionEvent evt) {
    movimentar(true);
}

private void movimentar(boolean entrada){
    int id;
    int qtd;
    try{
        id = Integer.parseInt(txt_id.getText().trim());
        qtd = Integer.parseInt(txt_movimento.getText().trim());
    }catch(NumberFormatException ex){
        JOptionPane.showMessageDialog(this, "Informe id e quantidade numericos");
        return;
    }
    if(qtd <= 0){
        JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero");
        return;
    }
    ExtrasDAO extras = new ExtrasDAO();
    boolean ok = entrada ? extras.entrada(id, qtd) : extras.baixa(id, qtd);
    if(ok){
        JOptionPane.showMessageDialog(this, entrada ? "Entrada registrada" : "Baixa registrada");
    }else{
        JOptionPane.showMessageDialog(this, "Nao foi possivel (id inexistente ou saldo insuficiente)");
    }
}

// Mostrar o id gerado após inserir
private void btn_salvarComIdActionPerformed(java.awt.event.ActionEvent evt) {
    Filho f = new Filho();
    f.setNome(txt_nome.getText().trim());
    f.setPreco(Double.parseDouble(txt_preco.getText().trim().replace(",", ".")));
    f.setQuantidade(Integer.parseInt(txt_quantidade.getText().trim()));
    f.setPai((Pai) cmb_pai.getSelectedItem());
    int novoId = new ExtrasDAO().inserirRetornandoId(f);
    if(novoId > 0){
        txt_id.setText(novoId + "");
        JOptionPane.showMessageDialog(this, "Cadastrado com o codigo " + novoId);
    }
}
```

### 8.5 Excluir pai que tem filhos: as 3 estratégias

| Estratégia | Como | Quando a prova pede |
|---|---|---|
| **Bloquear** (padrão) | sem `ON DELETE`; `PaiDAO.excluir` devolve `false`; tela avisa | "não permitir excluir categoria com produtos" |
| **Cascade** | `FOREIGN KEY ... ON DELETE CASCADE` no `CREATE TABLE` | "ao excluir o pai, excluir os filhos" (cuidado: some sem avisar) |
| **Excluir os filhos antes (no código)** | `ExtrasDAO.excluirPaiComFilhos` (transação) após confirmar | "avisar quantos filhos serão apagados e pedir confirmação" |

```java
// TELA: FormExcluirPai
private void btn_excluir_paiActionPerformed(java.awt.event.ActionEvent evt) {
    Pai pai = (Pai) cmb_pai.getSelectedItem();
    if(pai == null){
        return;
    }
    ExtrasDAO extras = new ExtrasDAO();
    int filhos = extras.contarFilhosDoPai(pai.getId());
    if(filhos > 0){
        // estratégia "excluir tudo": pergunta e apaga filhos + pai
        int r = JOptionPane.showConfirmDialog(this,
                "Este pai tem " + filhos + " filho(s). Excluir o pai e todos os filhos?",
                "Exclusao", JOptionPane.YES_NO_OPTION);
        if(r != JOptionPane.YES_OPTION){
            return;
        }
        extras.excluirPaiComFilhos(pai.getId());
    }else{
        // estratégia "bloquear": se mesmo assim falhar, o DAO devolve false
        if(!new PaiDAO().excluir(pai.getId())){
            JOptionPane.showMessageDialog(this, "Nao foi possivel excluir");
            return;
        }
    }
    JOptionPane.showMessageDialog(this, "Excluido");
}
```

Para só **bloquear** (sem apagar nada), troque o bloco `if(filhos > 0)` por uma mensagem
`JOptionPane.showMessageDialog(this, "Existem " + filhos + " filho(s) neste pai"); return;`.

### 8.6 Login simples com tabela `usuarios`

```sql
CREATE TABLE usuarios(
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(30) NOT NULL UNIQUE,
    senha VARCHAR(64) NOT NULL
);
INSERT INTO usuarios(login, senha) VALUES ('admin', '1234');
-- com hash (opcional): INSERT INTO usuarios(login, senha) VALUES ('admin', SHA2('1234', 256));
--                      e no DAO: ... AND senha = SHA2(?, 256)
```

```java
// ARQUIVO: DAO/UsuarioDAO.java
package DAO;

import conexao.Conexao;
import java.sql.*;

public class UsuarioDAO {
    Conexao conexao;
    Connection conn;

    public UsuarioDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }

    // true se existe usuário com esse login e senha
    public boolean autenticar(String login, String senha){
        String sql = "SELECT COUNT(*) AS qtd FROM usuarios WHERE login = ? AND senha = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("qtd") > 0;
        }catch(SQLException ex){
            System.out.println("Erro no login: " + ex.getMessage());
            return false;
        }
    }
}
```

Tela `FormLogin` (`txt_login` JTextField, `txt_senha` **JPasswordField**, `btn_entrar`) e **classe principal** do projeto = `view.FormLogin`:

```java
// TELA: FormLogin
private void btn_entrarActionPerformed(java.awt.event.ActionEvent evt) {
    String login = txt_login.getText().trim();
    String senha = new String(txt_senha.getPassword());     // getPassword() devolve char[]
    if(new UsuarioDAO().autenticar(login, senha)){
        new Menu().setVisible(true);
        this.dispose();                                      // fecha só o login
    }else{
        JOptionPane.showMessageDialog(this, "Login ou senha invalidos");
        txt_senha.setText("");
        txt_login.requestFocus();
    }
}
```

(`Menu` é o JFrame do menu principal; no repo das aulas é `ProjetoAulaBD/src/view/Menu.java`.) O login com senha em texto puro serve para a prova; em sistema real guarda-se o **hash**.

### 8.7 Pesquisar com JList, listagem em JTextArea, sair com confirmação, ID e formulário reaproveitado

```java
// TELA: FormExtrasUI
// --- JList de resultados (lst_filhos com Type Parameters <Filho>); no Bean Filho acrescente:
//     @Override public String toString() { return id + " - " + nome; }
private void pesquisarNaLista(){
    DefaultListModel<Filho> modelo = new DefaultListModel<>();
    for(Filho f : new FilhoDAO().buscar(txt_busca.getText().trim())){
        modelo.addElement(f);
    }
    lst_filhos.setModel(modelo);
}

private void lst_filhosMouseClicked(java.awt.event.MouseEvent evt) {
    Filho selecionado = lst_filhos.getSelectedValue();
    if(selecionado != null){
        txt_nome.setText(selecionado.getNome());
    }
}

// --- JTextArea (txt_area) como listagem simples
private void listarNoTextArea(){
    StringBuilder sb = new StringBuilder();
    for(Filho f : new FilhoDAO().getFilhos()){
        sb.append(f.getId()).append(" - ")
          .append(f.getNome()).append(" | ")
          .append(String.format("%.2f", f.getPreco())).append(" | ")
          .append(f.getPai().getNome()).append("\n");
    }
    txt_area.setText(sb.toString());
    txt_area.setEditable(false);
}

// --- Sair com confirmação ao clicar no X da janela: defaultCloseOperation = DO_NOTHING (Properties) e,
//     no construtor, depois do initComponents():  ativarConfirmacaoDeSaida();
private void ativarConfirmacaoDeSaida(){
    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            int r = JOptionPane.showConfirmDialog(FormExtrasUI.this, "Deseja fechar esta tela?", "Sair", JOptionPane.YES_NO_OPTION);
            if(r == JOptionPane.YES_OPTION){
                dispose();                  // fecha só esta tela (System.exit(0) fecharia o programa)
            }
        }
    });
}

// --- Campo ID habilitado/desabilitado
private void configurarCampoId(boolean podeDigitar){
    txt_id.setEnabled(podeDigitar);        // cinza e sem clique
    // txt_id.setEditable(false);          // alternativa: continua com aparência normal, mas não digita
}
```

Formulário **reaproveitado** para inserir e editar (a mesma tela abre vazia pelo menu ou preenchida por outra tela):

```java
// TELA: FormFilhoUnico
private Filho filhoEmEdicao;                     // null = cadastro novo

public FormFilhoUnico() {
    this(null);
}

public FormFilhoUnico(Filho filho) {
    initComponents();
    preencherComboPais();
    this.filhoEmEdicao = filho;
    if(filho != null){
        setTitle("Editar");
        txt_nome.setText(filho.getNome());
        txt_preco.setText(filho.getPreco() + "");
        txt_quantidade.setText(filho.getQuantidade() + "");
        cmb_pai.setSelectedItem(filho.getPai());
    }else{
        setTitle("Cadastrar");
    }
}

private void preencherComboPais(){
    cmb_pai.removeAllItems();
    for(Pai p : new PaiDAO().getPais()){
        cmb_pai.addItem(p);
    }
}

private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {
    Filho f = new Filho();
    f.setNome(txt_nome.getText().trim());
    try{
        f.setPreco(Double.parseDouble(txt_preco.getText().trim().replace(",", ".")));
        f.setQuantidade(Integer.parseInt(txt_quantidade.getText().trim()));
    }catch(NumberFormatException ex){
        JOptionPane.showMessageDialog(this, "Preco e quantidade devem ser numeros");
        return;
    }
    f.setPai((Pai) cmb_pai.getSelectedItem());
    if(filhoEmEdicao == null){
        new FilhoDAO().inserir(f);
    }else{
        f.setId(filhoEmEdicao.getId());
        new FilhoDAO().editar(f);
    }
    dispose();
}
// quem chama:  new FormFilhoUnico().setVisible(true);   ou   new FormFilhoUnico(filho).setVisible(true);
```

### 8.8 Ordenar ao clicar no cabeçalho da JTable

Opção 1 (GUI Builder): selecione a tabela > *Properties* > marque **autoCreateRowSorter**.
Opção 2 (código, depois do `initComponents()`):

```java
// TELA: FormOrdenacao
private void configurarTabelaOrdenavel(){
    tbl_filhos.setModel(new DefaultTableModel(
        new Object[][]{},
        new String[]{"ID", "Nome", "Preco", "Qtd", "Pai"}
    ){
        // dizer o tipo de cada coluna faz ID/Preco/Qtd ordenarem como NÚMERO (senão "10" fica antes de "9")
        @Override
        public Class<?> getColumnClass(int coluna) {
            switch(coluna){
                case 0:
                case 3:
                    return Integer.class;
                case 2:
                    return Double.class;
                default:
                    return String.class;
            }
        }
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    });
    tbl_filhos.setAutoCreateRowSorter(true);
}
```

Com o ordenador ligado, **não** coloque preço já formatado (`String.format`) na célula se quiser ordem numérica: passe `f.getPreco()` (double).
`tbl_filhos.getSelectedRow()` e `getValueAt(linha, coluna)` continuam certos (índices da **visão**).

### 8.9 Resumo: "o que mudar" por pedido da prova

| Pedido do enunciado | Onde mexer |
|---|---|
| "campo novo X" | os 6 lugares do 8.1 |
| "tela com combo de Y" | `YDAO.getYs()` + `preencherComboYs()` + `toString`/`equals` em Y |
| "relatório filtrado por ..." | parâmetro novo no `getFilhos(...)` (6.5) + componente no filtro (7.3) |
| "total / soma / contagem" | `Filho.getTotal()`, `ExtrasDAO` (8.4), `JLabel` |
| "não pode excluir pai com filhos" | `excluir` devolve `boolean` + mensagem (8.5) |
| "menu" | 7.5 |
| "login" | 8.6 |
| "tabela de ligação / matrícula" | 8.2 e 8.3 |

---

## 9. Erros e soluções

Os erros de SQL dos DAOs aparecem na janela **Output** do NetBeans (`System.out.println("Erro ...: " + e.getMessage())`). Leia sempre lá primeiro.

| Mensagem / sintoma | Causa | Solução |
|---|---|---|
| `Communications link failure` | MySQL desligado, porta errada ou host errado | iniciar o serviço MySQL80; conferir porta `3306` e `localhost`; testar no Workbench |
| `Access denied for user 'root'@'localhost'` | senha/usuário diferentes | senha do lab: `laboratorio`; conferir na `Conexao.java` (igual ao Workbench) |
| `Unknown database 'xxx'` | nome do banco na URL difere do `CREATE DATABASE`, ou o script não foi executado | rodar o script; conferir `SHOW DATABASES;` e o nome na URL |
| `Table 'meubanco.filhos' doesn't exist` | tabela não criada, ou nome diferente (no Linux, **maiúscula/minúscula importa**: `Filhos` ≠ `filhos`) | `SHOW TABLES;`; usar sempre minúsculas iguais no SQL e no Java |
| `No suitable driver found for jdbc:mysql://...` ou `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | o `.jar` não está nas **Libraries** | *Libraries > Add JAR/Folder* (seção 5.2); depois *Clean and Build* |
| `Cannot add or update a child row: a foreign key constraint fails` | FK com id de pai que não existe | escolher pai existente; inserir o pai primeiro; conferir `getId()` do combo |
| `Cannot delete or update a parent row: a foreign key constraint fails` (**1451**) | excluir pai que ainda tem filhos | excluir/mudar os filhos antes, `ON DELETE CASCADE`, ou bloquear com mensagem (seção 8.5) |
| `Public Key Retrieval is not allowed` | autenticação do MySQL 8 sem SSL | **só se aparecer**: URL `...?useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false` |
| `NumberFormatException: For input string: "10,50"` / `""` | vírgula decimal ou campo vazio em `parseDouble`/`parseInt` | `.trim().replace(",", ".")` e `try/catch NumberFormatException` |
| `NullPointerException` em `conn.prepareStatement` | `conn` é `null`: a conexão falhou (veja a mensagem no Output) | corrigir a conexão (linhas acima) |
| `NullPointerException` em `getSelectedItem()...` | combo vazio (nenhum pai cadastrado) | testar `if(pai == null)` antes de usar |
| Combo mostra "Item 1, Item 2..." | model padrão do GUI Builder | `removeAllItems()` antes de preencher; apagar os itens do model |
| `incompatible types: Pai cannot be converted to String` em `addItem` | combo é `JComboBox<String>` | aba *Code* > Type Parameters `<Pai>` |
| Combo mostra `Beans.Pai@1b6d3586` | falta `toString()` no Bean | sobrescrever `toString` (6.2) |
| Combo não seleciona o pai ao consultar | falta `equals`/`hashCode` no Bean `Pai` | implementar por `id` (6.2) |
| Tabela com linhas repetidas | faltou limpar antes de preencher | `modelo.setRowCount(0);` (ou `setNumRows(0)`) no início |
| Tabela abre vazia | `carregarTabela()` não é chamada no construtor | chamar depois de `initComponents()` |
| `Column count doesn't match value count at row 1` | número de colunas ≠ número de `?`/valores no `INSERT` | conferir lista de colunas e de `?` |
| `Unknown column 'xxx' in 'field list'` | nome de coluna errado, ou `rs.getString("xxx")` com nome que não está no `SELECT` | usar o nome/alias exato do SELECT (`pai_nome`) |
| `Data truncated for column` / `Data too long for column` | valor maior que o `VARCHAR(n)` ou tipo errado | aumentar a coluna (`ALTER ... MODIFY`) ou validar o tamanho |
| `Incorrect decimal value` / `Incorrect date value` | texto fora do formato | converter antes (8.1); data no formato `yyyy-MM-dd` |
| `No value specified for parameter 2` | faltou um `setXxx` (ou todos com índice 1) | índices 1, 2, 3... na ordem dos `?` |
| `Duplicate entry 'x' for key` | `UNIQUE`/`PRIMARY KEY` repetido | avisar o usuário; não inserir de novo |
| Fechar uma tela fecha o programa inteiro | `defaultCloseOperation = EXIT` na tela filha | trocar para **DISPOSE** (Properties) |
| Clicar no botão não faz nada | o evento não foi ligado | botão direito > Events > Action > actionPerformed (ou duplo clique) |
| `cannot find symbol` | falta `import`, nome digitado diferente, método em outro DAO | `Ctrl+Shift+I`; conferir maiúsculas |
| `Error Code: 1175` (Workbench) | Safe Updates | `SET SQL_SAFE_UPDATES = 0;` ou *Edit > Preferences > SQL Editor* (seção 4.4) |
| Editei o `.java` à mão e o Design quebrou / o botão não responde | o GUI Builder mantém o `.form` em sincronia com o `.java`; blocos `//GEN-BEGIN`/`//GEN-END` (cinza) **não podem ser editados** | altere só fora dos blocos gerados; se desincronizar, apague o componente e refaça no Design |
| Mensagens estranhas do NetBeans sobre `nbproject/private` | pasta de configuração local | ignore; em último caso feche o projeto e abra de novo (*Clean and Build*) |
| Ao entregar, "não achou o jar" | caminho absoluto do jar | adicionar com caminho **relativo** (`drivers/`), entregar a pasta `drivers` junto |

---

## 10. Checklist final e perguntas teóricas

### 10.1 Checklist de entrega

- [ ] Script `.sql` salvo (`DROP`/`CREATE DATABASE`, tabelas na ordem certa, `INSERT`s) e **executa do zero sem erro**.
- [ ] Banco e tabelas existem no MySQL (`SHOW TABLES;`) com dados de teste.
- [ ] Jar do MySQL nas **Libraries**; URL/usuário/senha/banco corretos na `Conexao`.
- [ ] Pacotes `conexao`, `Beans`, `DAO`, `view`; **1 Bean e 1 DAO por tabela**.
- [ ] Beans com `toString`/`equals`/`hashCode` nos "pais" usados em combo.
- [ ] DAOs: `inserir`, `getXxx(id)`, `getXxxs(...)`, `editar` (incluindo a FK), `excluir` (devolve `boolean`).
- [ ] Cadastro: combos preenchidos do banco, validação, mensagem de sucesso, formulário limpo.
- [ ] Consulta por ID: trata "não encontrado"; editar troca a FK; excluir pede confirmação.
- [ ] Relatório: JTable com `setRowCount(0)`, filtros funcionando, formatação do preço.
- [ ] Menu com todas as telas e Sair; telas filhas com **DISPOSE**.
- [ ] Classe principal definida (*Properties > Run*); `F6` abre o programa.
- [ ] Testei: inserir, consultar, atualizar, **trocar a FK**, excluir, excluir pai com filho, filtrar.
- [ ] *Clean and Build* sem erros; conferi os dados no Workbench; nome/arquivos como o enunciado pediu.

### 10.2 Perguntas teóricas

- **O que é um Bean?** Classe que representa uma tabela: atributos = colunas, `private` com getters/setters. Transporta dados entre tela e DAO.
- **O que é DAO?** *Data Access Object*: classe que concentra o acesso ao banco (SQL) de uma tabela, para a tela não conter SQL.
- **O que é JDBC?** API Java (`java.sql`) para falar com bancos relacionais; o **driver** (`.jar`) implementa para o MySQL.
- **Passos de uma operação JDBC?** obter `Connection` > `prepareStatement(sql)` > `setXxx` dos parâmetros > `execute()`/`executeQuery()` > ler `ResultSet` > tratar `SQLException`.
- **`PreparedStatement` × `Statement`?** O prepared usa parâmetros `?`: evita **SQL Injection**, trata aspas/tipos e reaproveita o comando; o `Statement` recebe o SQL concatenado (inseguro).
- **O que é SQL Injection?** Entrada do usuário que vira parte do SQL (ex.: `' OR '1'='1`) e altera a consulta.
- **`execute`/`executeUpdate` × `executeQuery`?** `executeQuery` é para `SELECT` (devolve `ResultSet`); os outros para `INSERT`/`UPDATE`/`DELETE` (`executeUpdate` devolve o número de linhas afetadas).
- **Chave primária (PK) × estrangeira (FK)?** PK identifica a linha de forma única; FK é coluna que referencia a PK de outra tabela e garante **integridade referencial**.
- **O que é relacionamento 1:N? E N:N?** 1:N: um pai tem vários filhos (FK no filho). N:N: precisa de **tabela de ligação** com duas FKs (ex.: `matriculas`).
- **O que é `JOIN`?** Junta linhas de tabelas relacionadas por uma condição (`ON p.id = f.pai_id`). `INNER` só traz o que tem par; `LEFT` traz todos da esquerda.
- **Por que um DAO por tabela?** Responsabilidade única, organização e reaproveitamento (o combo do cadastro usa o `PaiDAO`).
- **Por que `toString` e `equals` no Bean?** `toString` define o texto no combo/lista; `equals` (por id) permite `setSelectedItem` achar o objeto certo.
- **MVC / camadas?** *Model* = Beans (e DAO), *View* = telas Swing, *Controller* = eventos que ligam as duas. A tela nunca escreve SQL.
- **`DISPOSE_ON_CLOSE` × `EXIT_ON_CLOSE`?** DISPOSE fecha só a janela; EXIT encerra a aplicação.
- **Por que `setRowCount(0)` antes de preencher a tabela?** Para limpar as linhas antigas, senão duplica.
- **Por que `AUTO_INCREMENT`?** O banco gera o id; por isso o `INSERT` não passa o id.

---

## 11. Anexo: cola de uma página

### 11.1 Assinaturas do molde

```java
// Conexao
public Connection getConexao()

// PaiDAO
public void inserir(Pai pai)
public Pai getPai(int id)                       // null se não achou
public List<Pai> getPais(String nome)           // LIKE
public List<Pai> getPais()                      // todos
public void editar(Pai pai)
public boolean excluir(int id)                  // false se a FK bloqueou

// FilhoDAO
public void inserir(Filho filho)
public Filho getFilho(int id)
public List<Filho> getFilhos(String nome, String paiNome)
public List<Filho> getFilhos()
public List<Filho> getFilhos(String nome, int paiId, Double min, Double max, boolean soEstoque, String ordem)
public List<Filho> buscar(String termo)         // nome OU pai
public void editar(Filho filho)
public boolean excluir(int id)
```

### 11.2 Imports

```java
import Beans.Pai;            import Beans.Filho;
import DAO.PaiDAO;           import DAO.FilhoDAO;
import conexao.Conexao;
import java.sql.*;           // Connection, DriverManager, PreparedStatement, ResultSet, SQLException, Statement
import java.util.ArrayList;  import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
```

### 11.3 Snippets

```java
// JOptionPane
JOptionPane.showMessageDialog(this, "Mensagem");
int r = JOptionPane.showConfirmDialog(this, "Excluir?", "Exclusao", JOptionPane.YES_NO_OPTION);
if(r == JOptionPane.YES_OPTION){ /* ... */ }
String nome = JOptionPane.showInputDialog(this, "Digite o nome");     // null se cancelar

// DefaultTableModel
DefaultTableModel modelo = (DefaultTableModel) tbl_filhos.getModel();
modelo.setRowCount(0);                                   // limpa
modelo.addRow(new Object[]{ f.getId(), f.getNome() });   // linha nova
int linha = tbl_filhos.getSelectedRow();                 // -1 = nenhuma
Object valor = tbl_filhos.getValueAt(linha, 0);          // coluna 0 da linha

// ComboBox de objetos
cmb_pai.removeAllItems();
cmb_pai.addItem(pai);
Pai p = (Pai) cmb_pai.getSelectedItem();                 // pode ser null
cmb_pai.setSelectedItem(filho.getPai());                 // precisa de equals no Bean
cmb_pai.setSelectedIndex(0);

// RadioButton + ButtonGroup (grupo criado no Design; cada rádio com a propriedade buttonGroup)
if(rdo_m.isSelected()){ /* ... */ }
rdo_m.setSelected(true);
grp_sexo.clearSelection();

// Número / texto
double d = Double.parseDouble(txt_preco.getText().trim().replace(",", "."));
int i = Integer.parseInt(txt_quantidade.getText().trim());
txt_preco.setText(f.getPreco() + "");
String.format("%.2f", valor);

// Abrir outra tela / fechar esta / sair
new FormCadastroFilho().setVisible(true);
this.dispose();
System.exit(0);

// Foco e habilitar
txt_nome.requestFocus();
txt_id.setEnabled(false);
```

### 11.4 JDBC em 5 linhas

```java
PreparedStatement stmt = conn.prepareStatement("UPDATE filhos SET nome=?, preco=? WHERE id=?");
stmt.setString(1, nome);  stmt.setDouble(2, preco);  stmt.setInt(3, id);   // índices de 1; id por último
stmt.execute();                                      // INSERT / UPDATE / DELETE
ResultSet rs = stmt2.executeQuery();                 // SELECT
while(rs.next()){ String n = rs.getString("nome"); } // percorre as linhas
```

### 11.5 Atalhos do NetBeans

| Atalho | Ação |
|---|---|
| `F6` / `Shift+F6` | executar projeto / arquivo |
| `Shift+F11` | Clean and Build |
| `Ctrl+Shift+I` | corrigir imports |
| `Alt+Shift+F` | formatar código |
| `Alt+Insert` | gerar getters/setters/`equals`/construtor |
| `Ctrl+Espaço` | autocompletar |
| `Ctrl+/` | comentar/descomentar linha |

### 11.6 Pastas do repositório para consultar

| O que | Onde |
|---|---|
| Molde Pai/Filho completo (GUI Builder) | `java_swing_sql/Produtos/src/{conexao,Beans,DAO,view}` + [`../sql/produtos.sql`](../sql/produtos.sql) |
| Pessoa/Veículo (aulas 07 a 10) | `java_swing_sql/ProjetoAulaBD` + [`../sql/bancoAula.sql`](../sql/bancoAula.sql) |
| Aluno/Professor/Disciplina | `java_swing_sql/ProjetoBDescola` + [`../sql/escola.sql`](../sql/escola.sql) |
| Jar do driver | [`../libs/mysql-connector-j-8.1.0.jar`](../libs/mysql-connector-j-8.1.0.jar) |
