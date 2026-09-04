
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author laboratorio
 */
public class Arquivo {
    
    private FileWriter arqW;
    private BufferedWriter escritor;
    
    private FileReader arqR;
    private BufferedReader leitor;
    
    private ArrayList<Pessoa> listaPessoas;
    
    public String nomeArquivo;
    
    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        listaPessoas = new ArrayList<>();
    }
    
    
    public ArrayList<Pessoa> leArquivo() {
        
        listaPessoas.clear();
        
        try {
            arqR = new FileReader(nomeArquivo + ".txt");
            leitor = new BufferedReader(arqR);
            
            String linha;
            
            while ((linha = leitor.readLine()) != null) {
                
                String[] campos = linha.split(";");
                
                listaPessoas.add(new Pessoa(campos[0], campos[1].charAt(0), campos[2]));
                return listaPessoas;
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
        
        return listaPessoas;
    }
    
    
    public ArrayList<Pessoa> getListaPessoas() {
        return listaPessoas;
    }
    
    
    public void gravaArquivo() {
        
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo + ".txt", false))) {
               
            for (Pessoa p : listaPessoas) {
                
                escritor.write(p.nome + ";" + p.sexo + ";" + p.idioma);
                escritor.newLine();
            }
        }   
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
