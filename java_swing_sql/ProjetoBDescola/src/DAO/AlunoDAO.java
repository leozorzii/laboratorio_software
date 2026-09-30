/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Beans.Aluno;
import conexao.Conexao;
import java.sql.*;

/**
 *
 * @author laboratorio
 */
public class AlunoDAO {
    Conexao conexao;
    Connection conn;

    public AlunoDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }
    public void inserir(Aluno aluno){
        String sql = "INSERT INTO aluno(nome,idade,curso) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getIdade());
            stmt.setString(3, aluno.getCurso());
            stmt.execute();
        }catch(SQLException e){ 
            System.out.println("Erro ao inserir aluno" + e.getMessage());
        }
    }
    
}
