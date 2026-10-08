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
        String sql = "INSERT INTO professor(nome,idade,disciplina) VALUES (?, ?, ?)";
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
     public Professor getProfessor(int id){
        String sql = "SELECT * FROM professor WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Professor prof = new Professor();
            
            rs.first();
            prof.setId(id);
            prof.setNome(rs.getString("nome"));
            prof.setIdade(rs.getInt("idade"));
            prof.setDisciplina(rs.getString("disciplina"));
            return prof;
        }catch(SQLException ex){
            System.out.println("Erro ao consultar professor: " + ex.getMessage());
            return null;
        }
    }
    public void editar(Professor prof){
        try{
            String sql = "UPDATE pessoa set nome=?, idade=?, disciplina=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, prof.getNome());
            stmt.setInt(2, prof.getIdade());
            stmt.setString(3, prof.getDisciplina());
            stmt.setInt(4, prof.getId());
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao atualizar professor" +ex.getMessage());
        }
    }
    public void excluir(int id){
        try{
            String sql = "DELETE FROM professor WHERE id=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao excluir professor" +ex.getMessage());
        }
    }
    
}
