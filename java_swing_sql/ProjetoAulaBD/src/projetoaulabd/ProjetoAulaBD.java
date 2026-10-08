/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projetoaulabd;

import Beans.Pessoa;
import DAO.PessoaDAO;
import conexao.Conexao;

/**
 *
 * @author laboratorio
 */
public class ProjetoAulaBD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conexao c = new Conexao();
        c.getConexao();
        Pessoa p = new Pessoa();
        p.setNome("Paizao do goias");
        p.setSexo("M");
        p.setIdioma("Inglês");
        PessoaDAO pdao = new PessoaDAO();
        pdao.inserir(p);
    }
    
}
