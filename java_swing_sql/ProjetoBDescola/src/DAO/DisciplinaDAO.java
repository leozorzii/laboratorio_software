/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Beans.Disciplina;
import conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leo zorzi
 */
public class DisciplinaDAO {
    Conexao conexao;
    Connection conn;

    public DisciplinaDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }
    public void inserir(Disciplina disciplina){
        String sql = "INSERT INTO disciplina(nome,carga_horaria,id_professor) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, disciplina.getNome());
            stmt.setInt(2, disciplina.getCargaHoraria());
            stmt.setInt(3, disciplina.getProfessor().getId());
            stmt.execute();
        }catch(SQLException e){
            System.out.println("Erro ao inserir disciplina" + e.getMessage());
        }
    }
    public Disciplina getDisciplina(int id){
        String sql = "SELECT * FROM disciplina WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(!rs.first()){
                return null;
            }
            Disciplina d = new Disciplina();
            d.setId(id);
            d.setNome(rs.getString("nome"));
            d.setCargaHoraria(rs.getInt("carga_horaria"));
            ProfessorDAO profDAO = new ProfessorDAO();
            d.setProfessor(profDAO.getProfessor(rs.getInt("id_professor")));
            return d;
        }catch(SQLException ex){
            System.out.println("Erro ao consultar disciplina: " + ex.getMessage());
            return null;
        }
    }
    public List<Disciplina> getDisciplinas(){
        String sql = "SELECT * FROM disciplina";
        List<Disciplina> lista = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ProfessorDAO profDAO = new ProfessorDAO();
            while(rs.next()){
                Disciplina d = new Disciplina();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setCargaHoraria(rs.getInt("carga_horaria"));
                d.setProfessor(profDAO.getProfessor(rs.getInt("id_professor")));
                lista.add(d);
            }
        }catch(SQLException ex){
            System.out.println("Erro ao listar disciplinas: " + ex.getMessage());
        }
        return lista;
    }
    public void editar(Disciplina disciplina){
        try{
            String sql = "UPDATE disciplina set nome=?, carga_horaria=?, id_professor=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, disciplina.getNome());
            stmt.setInt(2, disciplina.getCargaHoraria());
            stmt.setInt(3, disciplina.getProfessor().getId());
            stmt.setInt(4, disciplina.getId());
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao atualizar disciplina" +ex.getMessage());
        }
    }
    public void excluir(int id){
        try{
            String sql = "DELETE FROM disciplina WHERE id=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao excluir disciplina" +ex.getMessage());
        }
    }

}
