/**
 *
 * @author laboratorio
 */
public class Aluno {
     String nome;
     String dataNascimento;
     char sexo;
     int matricula;
     String curso;
     String cpf;
     String rua;
     String numero;
     String bairro;
     String cidade;
     String cep;
     String estado;
     String telefone;

    public Aluno(String nomeCompleto, String dataNascimento, char sexo, int matricula,
                 String curso, String cpf, String rua, String numero, String bairro,
                 String cidade, String cep, String estado, String telefone) {
        this.nome = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.matricula = matricula;
        this.curso = curso;
        this.cpf = cpf;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
        this.estado = estado;
        this.telefone = telefone;
    }

    // Monta a linha que vai ser gravada no arquivo texto (campos separados por ";")
    public String getDadosFormatados() {
        return nome + ";" +
               dataNascimento + ";" +
               sexo + ";" +
               matricula + ";" +
               curso + ";" +
               cpf + ";" +
               rua + ";" +
               numero + ";" +
               bairro + ";" +
               cidade + ";" +
               cep + ";" +
               estado + ";" +
               telefone;
    }

    public Object[] obterDados() {
        return new Object[] { nome, dataNascimento, sexo, matricula, curso,
                              cpf, rua, numero, bairro, cidade, cep, estado, telefone };
    }

    @Override
    public String toString() {
        return "Aluno{" + "nome=" + nome + ", matricula=" + matricula
                + ", curso=" + curso + '}';
    }
}
