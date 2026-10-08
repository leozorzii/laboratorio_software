package gerenciamentoeventos;
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
    
    private ArrayList<Evento> listaEventos;
    
    public String nomeArquivo;
    
    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        listaEventos = new ArrayList<>();
    }
    
    
    public ArrayList<Evento> leArquivo() {
        
        listaEventos.clear();
        
        try {
            arqR = new FileReader(nomeArquivo + ".txt");
            leitor = new BufferedReader(arqR);
            
            String linha;
            
            while ((linha = leitor.readLine()) != null) {
                
                String[] campos = linha.split(";");
                
                listaEventos.add(new Evento(
                    campos[0],          //nomeEvento - STRING
                     campos[1],         //data - STRING
                      campos[2],        //local - String  
                        campos[3],      // tipo - string
                          campos[4],    // modalidade - String
                             campos[5] // situcao - String
                ));
                return listaEventos;
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
        
        return listaEventos;
    }
    
    
    public ArrayList<Evento> getListaPessoas() {
        return listaEventos;
    }
    
    
    public void gravaArquivo() {
        
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo + ".txt", false))) {
               
            for (Evento e : listaEventos) {
                
                escritor.write(e.nomeEvento + ";" + e.data + ";" + e.local + ";" + e.situacao + ";" + e.modalidade + ";" + e.tipo);
                escritor.newLine();
            }
        }   
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
