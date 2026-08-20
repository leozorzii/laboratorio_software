import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Aluno {
    private String nomeCompleto;
    private String dataNascimento;
    private char sexo;
    private int matricula;
    private String curso;
    private String cpf;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;
    private String estado;
    private String telefone;

    public Aluno(String nomeCompleto, String dataNascimento, char sexo, int matricula, 
                 String curso, String cpf, String rua, String numero, String bairro, 
                 String cidade, String cep, String estado, String telefone) {
        this.nomeCompleto = nomeCompleto;
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

    public String getDadosFormatados() {
    return nomeCompleto + ";" +
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
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Aluno> listaAlunos = new ArrayList<>();
        int opcao;

        do {
            System.out.println(" Sistema de Cadastro de Alunos ");
            System.out.println("1- Adicionar novo aluno");
            System.out.println("2- Listar alunos cadastrados");
            System.out.println("3- Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            if (opcao == 1) {
                System.out.print("Nome completo: ");
                String nome = scanner.nextLine();

                System.out.print("Data de nascimento (DD/MM/AAAA): ");
                String nascimento = scanner.nextLine();

                System.out.print("Sexo: ");
                String sexo = scanner.nextLine();

                System.out.print("Matrícula (número único): ");
                int matricula = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Curso: ");
                String curso = scanner.nextLine();

                System.out.print("CPF: ");
                String cpf = scanner.nextLine();

                System.out.print("Rua: ");
                String rua = scanner.nextLine();

                System.out.print("Número: ");
                String numero = scanner.nextLine();

                System.out.print("Bairro: ");
                String bairro = scanner.nextLine();

                System.out.print("Cidade: ");
                String cidade = scanner.nextLine();

                System.out.print("CEP: ");
                String cep = scanner.nextLine();

                System.out.print("Estado (Sigla, ex: RS): ");
                String estado = scanner.nextLine();

                System.out.print("Telefone de contato: ");
                String telefone = scanner.nextLine();

                Aluno novoAluno = new Aluno(nome, nascimento, 'M', matricula, curso, cpf, 
                                            rua, numero, bairro, cidade, cep, estado, telefone);
                listaAlunos.add(novoAluno);
                System.out.println("Aluno cadastrado com sucesso!");

            } else if (opcao == 2) {
                if (listaAlunos.isEmpty()) {
                    System.out.println("Nenhum aluno cadastrado na lista");
                } else {
                    for (Aluno a : listaAlunos) {
                        a.getDadosFormatados();
                    }
                }
            }
        } while (opcao != 3);

        System.out.println("Sistema encerrado.");
        scanner.close();
    }
}
