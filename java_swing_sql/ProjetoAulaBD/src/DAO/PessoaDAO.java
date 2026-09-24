/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Beans.Pessoa;
import conexao.Conexao;
import java.sql.*;

/**
 *
 * @author laboratorio
 */
public class PessoaDAO {
    Conexao conexao;
    Connection conn;

    public PessoaDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }
    public void inserir(Pessoa pessoa){
        String sql = "INSERT INTO pessoa(nome,sexo,idioma) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getSexo());
            stmt.setString(3, pessoa.getIdioma());
            stmt.execute();
        }catch(SQLException e){ 
            System.out.println("Erro ao inserir pessoa" + e.getMessage());
        }
    }
    
}
