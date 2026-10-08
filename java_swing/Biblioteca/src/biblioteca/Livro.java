/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package biblioteca;

/**
 *
 * @author leo zorzi
 */
public class Livro {
    public String titulo;
    public String autor;
    public String genero;
    public int ano_lancamento;
    public char emprestado; // 'S' ou 'N'
    public String nomeLeitor;

    public Livro(String titulo, String autor, String genero, int ano_lancamento, char emprestado, String nomeLeitor) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.ano_lancamento = ano_lancamento;
        this.emprestado = emprestado;
        this.nomeLeitor = nomeLeitor;
    }
    @Override
    public String toString() {
        return "Livro{" + "titulo=" + titulo + ", autor=" + autor + ", genero=" + genero + ", ano_lancamento=" + ano_lancamento + ", emprestado=" + emprestado + "nomeLeitor=" + nomeLeitor + '}';
    }
    
    public Object[] obterDados(){
         return new Object[]{titulo, autor, genero, ano_lancamento, emprestado, nomeLeitor};
    }
}
