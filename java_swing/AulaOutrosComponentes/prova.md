# 📚 Guia de Prova — CRUD com Java Swing

> Cola explicada para a prova prática. Cada seção tem **o esqueleto** (a lógica em passos),
> **o código pronto pra copiar** e **o porquê de cada linha**. Feito em cima dos projetos
> `AulaOutrosComponentes`, `CadastroAluno` e `CaixaEletronico`.

---

## 🧭 Índice

1. [A arquitetura das 3 camadas (leia isso primeiro)](#1-a-arquitetura-das-3-camadas)
2. [Componentes Swing — os "átomos"](#2-componentes-swing--os-átomos)
3. [JOptionPane — as janelinhas de mensagem](#3-joptionpane--as-janelinhas)
4. [A classe Model (Pessoa / Aluno)](#4-a-classe-model)
5. [A camada Arquivo (ler e gravar .txt)](#5-a-camada-arquivo)
6. [A tabela — DefaultTableModel](#6-a-tabela--defaulttablemodel)
7. [CREATE — botão Salvar/Cadastrar](#7-create--botão-salvarcadastrar)
8. [UPDATE — a lógica da bandeira linhaEdicao](#8-update--a-lógica-da-bandeira-linhaedicao)
9. [DELETE — botão Excluir com confirmação](#9-delete--botão-excluir)
10. [Caixa Eletrônico — validação de número](#10-caixa-eletrônico)
11. [Erros que caem em prova (decorar os nomes)](#11-erros-comuns)
12. [Checklist final](#12-checklist-final)

---

## 1. A arquitetura das 3 camadas

Todo projeto desses tem os dados vivendo em **três lugares** ao mesmo tempo. Entender isso
resolve 80% da prova.

| Lugar | O que é | Vive onde | Some quando? |
|---|---|---|---|
| `ArrayList<Aluno>` (`listaAlunos`) | **A fonte da verdade** | Memória RAM | Ao fechar o programa |
| Arquivo `.txt` | **A persistência** | Disco | Nunca (fica salvo) |
| `JTable` (a tabela na tela) | **O espelho** | Tela | Ao fechar o programa |

**A regra de ouro:** toda operação de CRUD (add / set / remove) segue o mesmo ritual de 3 passos:

```
1. Altera a LISTA        →  add() / set() / remove()   (muda a verdade em memória)
2. arquivo.gravaArquivo()→  despeja a lista no disco    (senão perde ao fechar)
3. carregarTabela()      →  reconstrói o espelho        (senão a tela mostra dado velho)
```

Se você esquecer o passo 2, a alteração some ao reabrir. Se esquecer o passo 3, a tela
mostra dado desatualizado. **Sempre os três, sempre nessa ordem.**

---

## 2. Componentes Swing — os "átomos"

Os métodos que você mais vai usar. Decore o que cada um **lê** (pega da tela) e o que **escreve** (põe na tela).

### `getText()` — LÊ o que está escrito
```java
String nome = txtNome.getText();          // pega o texto digitado no JTextField
```

### `setText(...)` — ESCREVE algo no componente
```java
txtNome.setText("João");                  // escreve "João" no campo
txtNome.setText("");                       // LIMPA o campo (string vazia)
lblSaldo.setText("Saldo: R$ " + saldo);   // atualiza um JLabel
```

### JRadioButton — `isSelected()` e `ButtonGroup`
Radio buttons servem pra escolher **UMA** opção entre várias. O `ButtonGroup` é o que garante
que só um fica marcado por vez.

```java
// No topo da classe:
char sexo;

// Ao salvar — descobre qual está marcado:
if (rdoMasculino.isSelected()) {
    sexo = 'M';
} else if (rdoFeminino.isSelected()) {
    sexo = 'F';
} else {
    JOptionPane.showMessageDialog(null, "Selecione um sexo", "Erro", JOptionPane.ERROR_MESSAGE);
}

// Ao editar — MARCA o radio certo:
if (a.sexo == 'M') {
    rdoMasculino.setSelected(true);
} else {
    rdoFeminino.setSelected(true);
}

// Ao limpar o formulário:
btnGrpSexo.clearSelection();               // desmarca todos
```

### JCheckBox — `isSelected()`
Checkbox é independente (pode marcar vários). Só lê true/false:
```java
boolean tecnologia = chk_Tecnologia.isSelected();
chk_Tecnologia.setSelected(false);         // desmarca ao limpar
```

### JComboBox — `getSelectedItem()` e `setSelectedItem(...)`
A caixa de seleção (dropdown):
```java
String idioma = (String) cmb_Idioma.getSelectedItem();   // LÊ o item escolhido (precisa do cast)
cmb_estado.setSelectedItem(a.estado);                    // SELECIONA um item (ao editar)
cmb_Idioma.setSelectedIndex(0);                          // volta pro primeiro item (ao limpar)
```
> ⚠️ `getSelectedItem()` devolve um `Object`, por isso o cast `(String)`. Alternativa:
> `cmb_Idioma.getSelectedItem() + ""` também transforma em String.

### JTextArea — `append(...)`
Área de texto grande. `append` **adiciona** no fim sem apagar o resto:
```java
txa_Texto.append(txt_Mensagem.getText() + "\n");   // adiciona a mensagem + quebra de linha
txt_Mensagem.setText("");                            // limpa o campo de digitação
```

---

## 3. JOptionPane — as janelinhas

Três tipos. Saber **quando usar cada um** é o que o professor cobra.

### a) `showMessageDialog` — só AVISA (botão OK)
```java
// Forma simples (só a mensagem):
JOptionPane.showMessageDialog(null, "Dados salvos com sucesso");

// Forma completa (mensagem + título + ícone):
JOptionPane.showMessageDialog(
    null,                              // componente pai (null = centraliza na tela)
    "Selecione uma pessoa",            // a mensagem
    "Erro",                            // o título da janela
    JOptionPane.ERROR_MESSAGE          // o ícone
);
```

**Ícones disponíveis:**
| Constante | Ícone |
|---|---|
| `JOptionPane.ERROR_MESSAGE` | ❌ vermelho |
| `JOptionPane.WARNING_MESSAGE` | ⚠️ amarelo |
| `JOptionPane.INFORMATION_MESSAGE` | ℹ️ azul |

### b) `showConfirmDialog` — PERGUNTA (Sim / Não) e devolve a resposta
Use quando a ação é **destrutiva** (excluir). Ele **retorna um int** que diz qual botão foi clicado.
```java
int res = JOptionPane.showConfirmDialog(
    null,
    "Deseja realmente excluir?",
    "Confirmação",
    JOptionPane.YES_NO_OPTION
);

if (res == JOptionPane.YES_OPTION) {       // YES_OPTION = usuário clicou "Sim"
    // só executa se confirmou
}
```

### c) `showInputDialog` — PERGUNTA e devolve o TEXTO digitado
```java
String nome = JOptionPane.showInputDialog("Digite seu nome:");
```

> **Regra de bolso:** avisar → `showMessageDialog`. Pedir confirmação → `showConfirmDialog`.
> Pedir um valor → `showInputDialog`.

---

## 4. A classe Model

A classe que representa **uma linha** dos dados. Tem: atributos, construtor, `obterDados()`
(pra jogar na tabela) e às vezes um método que formata a linha do arquivo.

```java
public class Aluno {
    String nome;
    String dataNascimento;
    char   sexo;
    int    matricula;
    String curso;
    // ... resto dos campos

    // Construtor: recebe tudo e preenche os atributos
    public Aluno(String nome, String dataNascimento, char sexo, int matricula, String curso /*...*/) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.matricula = matricula;
        this.curso = curso;
        // ...
    }

    // Monta a linha do arquivo (campos separados por ";")
    public String getDadosFormatados() {
        return nome + ";" + dataNascimento + ";" + sexo + ";" + matricula + ";" + curso /* + ";" + ... */;
    }

    // Devolve os dados como Object[] pra virar uma LINHA da tabela
    public Object[] obterDados() {
        return new Object[] { nome, dataNascimento, sexo, matricula, curso /*, ...*/ };
    }
}
```

> 💡 A **ordem** dos campos em `getDadosFormatados()`, em `obterDados()` e nas colunas da tabela
> tem que ser **a mesma**, senão os dados aparecem trocados de coluna.

---

## 5. A camada Arquivo

Duas operações espelhadas: `gravaArquivo()` (objeto → texto) e `leArquivo()` (texto → objeto).

### Atributos e construtor
```java
public class Arquivo {
    private FileReader arqR;
    private BufferedReader leitor;
    private ArrayList<Aluno> listaAlunos;
    public String nomeArquivo;

    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        listaAlunos = new ArrayList<>();
    }
```

### `gravaArquivo()` — objeto → texto (fácil, conversão automática)
```java
public void gravaArquivo() {
    // "false" = SOBRESCREVE o arquivo inteiro (apaga e regrava do zero)
    try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo + ".txt", false))) {
        for (Aluno a : listaAlunos) {
            escritor.write(a.getDadosFormatados());   // escreve a linha
            escritor.newLine();                        // pula pra próxima linha
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```
> **Por que `false`?** A `listaAlunos` já é a verdade completa. É mais simples apagar tudo e
> reescrever a lista inteira do que caçar uma linha específica dentro do arquivo. Se fosse `true`,
> ele **anexaria** no fim e duplicaria tudo a cada salvamento.

### `leArquivo()` — texto → objeto (trabalhoso, conversão manual)
```java
public ArrayList<Aluno> leArquivo() {
    listaAlunos.clear();                          // esvazia antes de reencher

    try {
        arqR = new FileReader(nomeArquivo + ".txt");
        leitor = new BufferedReader(arqR);

        String linha;
        while ((linha = leitor.readLine()) != null) {   // lê linha por linha até acabar (null = fim)

            String[] campos = linha.split(";");           // quebra a linha nos ";"

            if (campos.length < 13) {                     // guard clause: linha incompleta? pula
                continue;
            }

            listaAlunos.add(new Aluno(
                campos[0],                          // nome      (String direto)
                campos[1],                          // dataNasc  (String direto)
                campos[2].charAt(0),                // sexo      String "M" → char 'M'
                Integer.parseInt(campos[3]),        // matricula String "123" → int 123
                campos[4]                           // curso
                /* ... resto dos campos ... */
            ));
        }
        leitor.close();
        arqR.close();
    } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());        // arquivo não existe ainda (1ª execução)
    } catch (Exception e) {
        e.printStackTrace();
    }
    return listaAlunos;
}
```

### 🔑 As conversões de tipo (a pegadinha da prova)

`split(";")` **sempre devolve Strings**. Só o que NÃO é String precisa ser convertido na mão:

| Tipo do campo | Gravando (ida — automática) | Lendo (volta — manual) |
|---|---|---|
| `String` | direto | `campos[i]` |
| `char` | vira texto sozinho no `+` | `campos[i].charAt(0)` |
| `int` | vira texto sozinho no `+` | `Integer.parseInt(campos[i])` |
| `double` | vira texto sozinho no `+` | `Double.parseDouble(campos[i])` |

> **Por que a ida é automática e a volta é manual?**
> O operador `+` com uma String converte o outro lado pra texto sozinho (virar texto sempre dá certo).
> Já a volta pode falhar (`"abc"` não vira número), então o Java te obriga a pedir com `parseInt`/
> `parseDouble` e assumir o risco de dar `NumberFormatException`.

---

## 6. A tabela — DefaultTableModel

A `JTable` não guarda os dados; quem guarda é o **model** (`DefaultTableModel`).

### Criar o model com colunas
```java
DefaultTableModel modeloTabela = new DefaultTableModel(
    new String[] { "Nome", "Data Nasc", "Sexo", "Matricula", "Curso" /*...*/ },  // cabeçalhos
    0                                                                             // 0 linhas iniciais
) {
    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;   // impede o usuário de editar direto na célula
    }
};
tbl_alunos = new JTable(modeloTabela);
```

### `carregarTabela()` — o método mais importante da tela
```java
private void carregarTabela() {
    DefaultTableModel tabela = (DefaultTableModel) tbl_pessoas.getModel();  // pega o model
    tabela.setRowCount(0);                     // LIMPA a tabela (zera as linhas)
    for (Aluno a : listaAlunos) {              // percorre a lista
        tabela.addRow(a.obterDados());         // adiciona cada aluno como uma linha
    }
}
```
> **Sempre `setRowCount(0)` antes do for.** Senão, cada vez que você recarrega, as linhas
> antigas continuam lá e a tabela vai duplicando os dados.

### Ler qual linha o usuário selecionou
```java
int linha = tbl_alunos.getSelectedRow();   // devolve o índice da linha, ou -1 se nada selecionado
```

---

## 7. CREATE — botão Salvar/Cadastrar

O esqueleto de **7 fases** que serve pra qualquer cadastro:

```
1. Coletar     → getText / isSelected / getSelectedItem
2. Validar     → converter int/char, com JOptionPane de erro se falhar
3. Montar      → new Aluno(...)
4. Decidir     → add (novo) ou set (edição)   ← ver seção 8
5. Persistir   → arquivo.gravaArquivo()
6. Atualizar   → carregarTabela()
7. Feedback    → JOptionPane de sucesso + limpar campos
```

Código completo:

```java
private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {

    // ---- FASE 1: coletar ----
    String nome    = txtNome.getText();
    String curso   = txtCurso.getText();
    String estado  = cmb_estado.getSelectedItem().toString();

    // ---- FASE 2: validar/converter ----
    int matricula;
    try {
        matricula = Integer.parseInt(txtMatricula.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Matrícula inválida: digite só números.",
                "Erro", JOptionPane.ERROR_MESSAGE);
        return;                            // guard clause: para tudo se a matrícula for inválida
    }

    char sexo;
    if (rdo_masculino.isSelected()) {
        sexo = 'M';
    } else if (rdo_feminino.isSelected()) {
        sexo = 'F';
    } else {
        JOptionPane.showMessageDialog(this, "Selecione o sexo.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // ---- FASE 3: montar o objeto ----
    Aluno aluno = new Aluno(nome, txtData.getText(), sexo, matricula, curso /*, ...*/);

    // ---- FASE 4: decidir add ou set (ver seção 8) ----
    if (linhaEdicao == -1) {
        listaAlunos.add(aluno);            // cadastro novo
    } else {
        listaAlunos.set(linhaEdicao, aluno);  // atualiza o existente
        linhaEdicao = -1;                  // REARMA a bandeira (não esquecer!)
    }

    // ---- FASE 5 + 6: persistir e atualizar ----
    arquivo.gravaArquivo();
    carregarTabela();

    // ---- FASE 7: feedback + limpar ----
    JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
    txtNome.setText("");
    txtData.setText("");
    txtMatricula.setText("");
    txtCurso.setText("");
    btnGrpSexo.clearSelection();
}
```

---

## 8. UPDATE — a lógica da bandeira linhaEdicao

O update **não tem botão próprio**. Ele reaproveita o botão Salvar. O truque é uma variável-bandeira:

```java
private int linhaEdicao = -1;   // -1 = "não estou editando ninguém, é cadastro novo"
```

### Como funciona o ciclo completo

```
1. Usuário CLICA numa linha da tabela e aperta EDITAR
2. btnEditar guarda o índice em linhaEdicao e joga os dados nos campos (setText)
3. Usuário altera o que quiser
4. Usuário aperta SALVAR
5. Salvar vê que linhaEdicao != -1  →  faz set() em vez de add()  →  ATUALIZA
6. Salvar rearma linhaEdicao = -1   →  próximo salvar volta a ser add() normal
```

### O botão Editar (carrega os dados na tela)
```java
private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
    int linha = tbl_alunos.getSelectedRow();

    if (linha == -1) {                     // guard clause: ninguém selecionado?
        JOptionPane.showMessageDialog(null, "Selecione um aluno para editar");
        return;
    }

    linhaEdicao = linha;                   // ⭐ guarda a linha — é isso que "arma" o modo edição
    Aluno a = listaAlunos.get(linha);      // pega o objeto daquela linha

    // Joga os dados de volta nos campos (setText):
    txtNome.setText(a.nome);
    txtData.setText(a.dataNascimento);
    txtMatricula.setText(String.valueOf(a.matricula));   // int → String
    txtCurso.setText(a.curso);

    if (a.sexo == 'M') {
        rdo_masculino.setSelected(true);
    } else {
        rdo_feminino.setSelected(true);
    }
    cmb_estado.setSelectedItem(a.estado);
}
```

> **Os dois `-1` são diferentes!**
> - `linhaEdicao == -1` → "não estou em modo edição" (bandeira do programa)
> - `getSelectedRow() == -1` → "o usuário não clicou em nenhuma linha" (estado da tabela)

> **Por que rearmar `linhaEdicao = -1` depois do set?** Se esquecer, a bandeira fica "presa"
> na última linha editada. Aí o próximo cadastro NOVO vai fazer `set()` por engano e
> **sobrescrever** um registro existente em vez de adicionar. Bug clássico.

> **`int → String`:** pra colocar a matrícula (int) num JTextField, use `String.valueOf(a.matricula)`
> ou `a.matricula + ""`.

---

## 9. DELETE — botão Excluir

Sempre com **confirmação**, porque é destrutivo e irreversível.

```java
private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {
    int linha = tbl_alunos.getSelectedRow();

    if (linha == -1) {                     // guard clause
        JOptionPane.showMessageDialog(null, "Selecione uma pessoa na tabela.",
                "Atenção", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int res = JOptionPane.showConfirmDialog(   // PERGUNTA e guarda a resposta
        null, "Deseja realmente excluir esta pessoa?",
        "Confirmação", JOptionPane.YES_NO_OPTION
    );

    if (res == JOptionPane.YES_OPTION) {       // só remove se confirmou
        listaAlunos.remove(linha);             // 1. altera a lista
        arquivo.gravaArquivo();                // 2. regrava o arquivo
        carregarTabela();                      // 3. atualiza a tela
    }
}
```

Repare no **trio final** `remove → gravaArquivo → carregarTabela` — é o mesmo ritual de 3 passos
da seção 1, aparecendo de novo.

---

## 10. Caixa Eletrônico

Lógica pura, sem arquivo nem tabela. Padrão: `getText` → validar/converter → regra de negócio → `setText`.

```java
private double saldo = 1000;
private boolean saldoVisivel = true;

// Método auxiliar: lê o texto e converte pra número com validação
public double lerEValidarValor() {
    String texto = textSaldo.getText().trim().replace(",", ".");   // troca vírgula por ponto
    double valor = Double.parseDouble(texto);                       // String → double
    if (valor <= 0) {
        throw new IllegalArgumentException("Informe um valor maior que 0");
    }
    return valor;
}

// DEPOSITAR
private void DepositarActionPerformed(java.awt.event.ActionEvent evt) {
    try {
        double valor = lerEValidarValor();
        saldo += valor;                                  // soma no saldo
        lblStatus.setText("Depósito de R$ " + valor + " efetuado!");
        textSaldo.setText("");
        atualizarSaldo();
    } catch (NumberFormatException e) {
        lblStatus.setText("Digite um número válido");
    } catch (IllegalArgumentException e) {
        lblStatus.setText("Erro: " + e.getMessage());
    }
}

// SACAR
private void SacarActionPerformed(java.awt.event.ActionEvent evt) {
    try {
        double valor = lerEValidarValor();
        if (valor > saldo) {                             // regra de negócio
            lblStatus.setText("Saldo insuficiente!");
            return;                                      // não deixa sacar
        }
        saldo -= valor;                                  // subtrai do saldo
        lblStatus.setText("Saque de R$ " + valor + " efetuado!");
        textSaldo.setText("");
        atualizarSaldo();
    } catch (NumberFormatException e) {
        lblStatus.setText("Digite um número válido");
    }
}

// CONSULTAR/OCULTAR SALDO (alterna com boolean)
private void consultarSaldoActionPerformed(java.awt.event.ActionEvent evt) {
    saldoVisivel = !saldoVisivel;                        // inverte o boolean
    consultarSaldo.setText(saldoVisivel ? "Ocultar Saldo" : "Apresentar Saldo");
    atualizarSaldo();
}

// Atualiza o label do saldo (respeitando se está visível ou oculto)
public void atualizarSaldo() {
    if (saldoVisivel) {
        lblSaldo.setText("Saldo Atual: R$ " + saldo);
    } else {
        lblSaldo.setText("Saldo Atual: R$ ***,**");
    }
}
```

> **Pontos de atenção do saque:** o código original tinha um bug — mesmo com saldo insuficiente
> ele continuava e subtraía. A correção é o `return;` logo depois do aviso, pra barrar o saque.

> **`.trim()`** remove espaços em branco antes/depois. **`.replace(",", ".")`** deixa o usuário
> digitar com vírgula (padrão BR), já que `Double.parseDouble` só entende ponto.

---

## 11. Erros comuns (decore os nomes)

| Erro | Quando acontece | Como evitar |
|---|---|---|
| `IndexOutOfBoundsException` | `lista.get(-1)` ou índice inexistente | Guard clause `if (linha == -1) return;` |
| `NumberFormatException` | `Integer.parseInt("abc")` — texto não-numérico | `try/catch` na conversão |
| `NullPointerException` | usar algo que é `null` | Checar antes de usar |
| `ArrayIndexOutOfBoundsException` | `campos[12]` numa linha curta | `if (campos.length < 13) continue;` |

**Guard clause (cláusula de guarda):** validar a condição ruim no começo e sair com `return`,
em vez de aninhar tudo num `if` gigante. Aparece no Editar, no Excluir e na validação da matrícula.

**`return;` num método `void`:** encerra o método na hora, **não executa nada abaixo**. É o que
protege o código de rodar com dados inválidos.

---

## 12. Checklist final

Antes de entregar qualquer operação, confira:

- [ ] **CREATE:** coletou tudo → validou int/char → montou objeto → add → gravou → recarregou → limpou campos
- [ ] **READ:** `leArquivo` faz `clear()`, lê linha a linha, `split(";")`, converte int/char, retorna a lista
- [ ] **UPDATE:** botão Editar guarda `linhaEdicao` e faz `setText`; botão Salvar checa `linhaEdicao == -1` (add vs set) e **rearma pra -1**
- [ ] **DELETE:** guard clause → `showConfirmDialog` → `if YES_OPTION` → remove → gravou → recarregou
- [ ] Todo botão que usa a tabela tem `if (getSelectedRow() == -1) return;`
- [ ] Toda alteração na lista é seguida de `gravaArquivo()` **e** `carregarTabela()`
- [ ] `carregarTabela()` faz `setRowCount(0)` antes do for
- [ ] Conversões: `charAt(0)` pra char, `Integer.parseInt` pra int, `Double.parseDouble` pra double
- [ ] Voltar valor pro campo: `String.valueOf(numero)` ou `numero + ""`

---

### 🎯 O mantra da prova

> **Toda operação mexe em 3 lugares: a LISTA (verdade), o ARQUIVO (persistência) e a TABELA (espelho).**
> Alterou a lista? Grava o arquivo e recarrega a tabela. Sempre os três.