package cadastroproduto;

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
    
    private ArrayList<Produto> listaProdutos;
    
    public String nomeArquivo;
    
    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        listaProdutos = new ArrayList<>();
    }
    
    
    public ArrayList<Produto> leArquivo() {
        
        listaProdutos.clear();
        
        try {
            arqR = new FileReader(nomeArquivo + ".txt");
            leitor = new BufferedReader(arqR);
            
            String linha;
            
            while ((linha = leitor.readLine()) != null) {
                
                String[] campos = linha.split(";");
                //ajustar campos de acordo com o arquivo Produto.java
                listaProdutos.add(new Produto(
                    campos[0],    //nome - string
                     campos[1],   //categoria - string
                      Double.parseDouble(campos[2]), //preco - double
                        Integer.parseInt(campos[3]), //quantidade - int
                          campos[4].charAt(0)        //disponivel - char
                ));
                return listaProdutos;
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
        
        return listaProdutos;
    }
    
    
    public ArrayList<Produto> getListaProdutos() {
        return listaProdutos;
    }
    
    
    public void gravaArquivo() {
        
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo + ".txt", false))) {
               
            for (Produto p : listaProdutos) {
                
                escritor.write(p.nome + ";" + p.categoria + ";" + p.preco +";" + p.quantidade + ";" + p.disponivel);
                escritor.newLine();
            }
        }   
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
