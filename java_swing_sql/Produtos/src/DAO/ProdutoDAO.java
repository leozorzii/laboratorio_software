package DAO;

import Beans.Categoria;
import Beans.Produto;
import conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD da tabela produtos. As consultas usam JOIN para ja trazer o nome da categoria.
 *
 * @author leo zorzi
 */
public class ProdutoDAO {
    Conexao conexao;
    Connection conn;

    // SELECT base usado em getProduto e getProdutos
    private static final String SELECT_BASE =
        "SELECT p.id, p.nome, p.preco, p.quantidade, p.categoria_id, c.nome AS categoria_nome "
        + "FROM produtos p INNER JOIN categorias c ON c.id = p.categoria_id ";

    public ProdutoDAO() {
        this.conexao = new Conexao();
        this.conn = conexao.getConexao();
    }

    public void inserir(Produto produto){
        String sql = "INSERT INTO produtos(nome, preco, quantidade, categoria_id) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setInt(4, produto.getCategoria().getId());
            stmt.execute();
        }catch(SQLException e){
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }

    private Produto montar(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setId(rs.getInt("categoria_id"));
        c.setNome(rs.getString("categoria_nome"));
        Produto p = new Produto();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setPreco(rs.getDouble("preco"));
        p.setQuantidade(rs.getInt("quantidade"));
        p.setCategoria(c);
        return p;
    }

    public Produto getProduto(int id){
        String sql = SELECT_BASE + "WHERE p.id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                return null;
            }
            return montar(rs);
        }catch(SQLException ex){
            System.out.println("Erro ao consultar produto: " + ex.getMessage());
            return null;
        }
    }

    // Busca por nome E/OU categoria. Campo vazio ("") nao filtra nada.
    public List<Produto> getProdutos(String nome, String categoria){
        String sql = SELECT_BASE + "WHERE p.nome LIKE ? AND c.nome LIKE ? ORDER BY p.nome";
        List<Produto> lista = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            stmt.setString(2, "%" + categoria + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lista.add(montar(rs));
            }
        }catch(SQLException ex){
            System.out.println("Erro ao listar produtos: " + ex.getMessage());
        }
        return lista;
    }

    public List<Produto> getProdutos(){
        return getProdutos("", "");
    }

    // inclui categoria_id: e assim que a categoria do produto e trocada
    public void editar(Produto produto){
        try{
            String sql = "UPDATE produtos SET nome=?, preco=?, quantidade=?, categoria_id=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setInt(4, produto.getCategoria().getId());
            stmt.setInt(5, produto.getId());
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao atualizar produto: " + ex.getMessage());
        }
    }

    public void excluir(int id){
        try{
            String sql = "DELETE FROM produtos WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao excluir produto: " + ex.getMessage());
        }
    }
}
