
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author laboratorio
 */
public class Arquivo {

    private FileReader arqR;
    private BufferedReader leitor;

    private ArrayList<Aluno> listaAlunos;

    public String nomeArquivo;

    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        listaAlunos = new ArrayList<>();
    }

    public ArrayList<Aluno> leArquivo() {

        listaAlunos.clear();

        try {
            arqR = new FileReader(nomeArquivo + ".txt");
            leitor = new BufferedReader(arqR);

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] campos = linha.split(";");

                if (campos.length < 13) {
                    continue;
                }

                listaAlunos.add(new Aluno(
                        campos[0],                          // nomeCompleto
                        campos[1],                          // dataNascimento
                        campos[2].charAt(0),                // sexo 
                        Integer.parseInt(campos[3]),        // matricula 
                        campos[4],                          // curso
                        campos[5],                          // cpf
                        campos[6],                          // rua
                        campos[7],                          // numero
                        campos[8],                          // bairro
                        campos[9],                          // cidade
                        campos[10],                         // cep
                        campos[11],                         // estado
                        campos[12]                          // telefone
                ));
            }

            leitor.close();
            arqR.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return listaAlunos;
    }

    public ArrayList<Aluno> getListaAlunos() {
        return listaAlunos;
    }

    public void gravaArquivo() {

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo + ".txt", false))) {

            for (Aluno a : listaAlunos) {

                escritor.write(a.getDadosFormatados());
                escritor.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
