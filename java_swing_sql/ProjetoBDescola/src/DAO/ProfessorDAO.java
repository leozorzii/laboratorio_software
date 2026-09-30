/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Beans.Professor;
import conexao.Conexao;
import java.sql.*;

/**
 *
 * @author laboratorio
 */
public class ProfessorDAO {
    Conexao conexao;
    Connection conn;

    public ProfessorDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }
    public void inserir(Professor professor){
        String sql = "INSERT INTO pessoa(nome,idade,disciplina) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, professor.getNome());
            stmt.setInt(2, professor.getIdade());
            stmt.setString(3, professor.getDisciplina());
            stmt.execute();
        }catch(SQLException e){ 
            System.out.println("Erro ao inserir Professor" + e.getMessage());
        }
    }
    
}
