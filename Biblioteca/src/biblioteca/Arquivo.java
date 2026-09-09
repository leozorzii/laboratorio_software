package biblioteca;

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
    
    private ArrayList<Livro> listaLivros;
    
    public String nomeArquivo;
    
    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        listaLivros = new ArrayList<>();
    }
    
    
    public ArrayList<Livro> leArquivo() {
        
        listaLivros.clear();
        
        try {
            arqR = new FileReader(nomeArquivo + ".txt");
            leitor = new BufferedReader(arqR);
            
            String linha;
            
            while ((linha = leitor.readLine()) != null) {
                
                String[] campos = linha.split(";");
                //ajustar campos de acordo com o arquivo Produto.java
                listaLivros.add(new Livro(
                      campos[0],                       //titulo
                      campos[1],                      //autor
                      campos[2],                     //genero
                      Integer.parseInt(campos[3]),    //ano_lancamento
                      campos[4].charAt(0),            //emprestado
                      campos[5]                       //nomeLeitor
                ));
                return listaLivros;
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
        
        return listaLivros;
    }
    
    
    public ArrayList<Livro> getListaLivros() {
        return listaLivros;
    }
    
    
    public void gravaArquivo() {
        
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo + ".txt", false))) {
               
            for (Livro l : listaLivros) {
                
                escritor.write(l.titulo + ";" + l.autor + ";" + l.genero + ";" + l.ano_lancamento + ":" + l.emprestado + ";" + l.nomeLeitor);
                escritor.newLine();
            }
        }   
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
