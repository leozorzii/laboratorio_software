package DAO;

import Beans.Categoria;
import conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD da tabela categorias.
 *
 * @author leo zorzi
 */
public class CategoriaDAO {
    Conexao conexao;
    Connection conn;

    public CategoriaDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }

    public void inserir(Categoria categoria){
        String sql = "INSERT INTO categorias(nome) VALUES (?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.execute();
        }catch(SQLException e){
            System.out.println("Erro ao inserir categoria: " + e.getMessage());
        }
    }

    public Categoria getCategoria(int id){
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(!rs.first()){
                return null;
            }
            Categoria c = new Categoria();
            c.setId(id);
            c.setNome(rs.getString("nome"));
            return c;
        }catch(SQLException ex){
            System.out.println("Erro ao consultar categoria: " + ex.getMessage());
            return null;
        }
    }

    // nome vazio ("") traz todas; senao filtra com LIKE
    public List<Categoria> getCategorias(String nome){
        String sql = "SELECT * FROM categorias WHERE nome LIKE ? ORDER BY nome";
        List<Categoria> lista = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                lista.add(c);
            }
        }catch(SQLException ex){
            System.out.println("Erro ao listar categorias: " + ex.getMessage());
        }
        return lista;
    }

    public List<Categoria> getCategorias(){
        return getCategorias("");
    }

    public void editar(Categoria categoria){
        try{
            String sql = "UPDATE categorias SET nome=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getId());
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao atualizar categoria: " + ex.getMessage());
        }
    }

    // devolve false se nao conseguiu (ex.: ainda existem produtos nessa categoria -> FK)
    public boolean excluir(int id){
        try{
            String sql = "DELETE FROM categorias WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        }catch(SQLException ex){
            System.out.println("Erro ao excluir categoria: " + ex.getMessage());
            return false;
        }
    }
}
