/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastroproduto;

/**
 *
 * @author leo zorzi
 */
public class Produto {
    public String nome;
    public String categoria;
    public double preco;
    public int quantidade;
    public char disponivel;
    
    public Produto(String nome, String categoria, double preco, int quantidade, char disponivel) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Produto{" + "nome=" + nome + ", categoria=" + categoria + ", preco=" + preco + ", quantidade=" + quantidade + ", disponivel=" + disponivel + '}';
    }
    
    
    public Object[] obterDados(){
        return new Object[]{nome, categoria, preco, quantidade, disponivel};
    }
}

