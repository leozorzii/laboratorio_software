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
     public Aluno getAluno(int id){
        String sql = "SELECT * FROM aluno WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Aluno a = new Aluno();
            
            rs.first();
            a.setId(id);
            a.setNome(rs.getString("nome"));
            a.setIdade(rs.getInt("idade"));
            a.setCurso(rs.getString("curso"));
            return a;
        }catch(SQLException ex){
            System.out.println("Erro ao consultar aluno: " + ex.getMessage());
            return null;
        }
    }
    public void editar(Aluno aluno){
        try{
            String sql = "UPDATE aluno set nome=?, idade=?, curso=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getIdade());
            stmt.setString(3, aluno.getCurso());
            stmt.setInt(4, aluno.getId());
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao atualizar aluno" +ex.getMessage());
        }
    }
    public void excluir(int id){
        try{
            String sql = "DELETE FROM aluno WHERE id=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao excluir aluno" +ex.getMessage());
        }
    }
    
}
