/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laboratorio
 */
public class Conexao {
    public Connection getConexao(){
        Connection conn;
        try {
            conn = DriverManager.getConnection(
             "jdbc:mysql://localhost:3306/aula01?useTimezone=true&serverTimezone=UTC","root", "laboratorio"
            );
            System.out.println("Conexao realizada com sucesso!");
            return conn;
        }catch(Exception e){
            System.out.println("Errp ao conectar no DB"+e.getMessage());
            return null;
        }
    }
}
