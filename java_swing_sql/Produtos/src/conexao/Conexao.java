package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Abre a conexao com o MySQL. Banco: loja (script: sql/produtos.sql).
 * Usuario e senha do laboratorio: root / laboratorio.
 *
 * @author leo zorzi
 */
public class Conexao {
    public Connection getConexao(){
        Connection conn;
        try {
            conn = DriverManager.getConnection(
             "jdbc:mysql://localhost:3306/loja?useTimezone=true&serverTimezone=UTC","root", "laboratorio"
            );
            System.out.println("Conexao realizada com sucesso!");
            return conn;
        }catch(Exception e){
            System.out.println("Erro ao conectar no DB: "+e.getMessage());
            return null;
        }
    }
}
