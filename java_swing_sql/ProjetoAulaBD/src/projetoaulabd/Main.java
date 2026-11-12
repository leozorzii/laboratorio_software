/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetoaulabd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author laboratorio
 */
public class Main {
    public static void main(String[] args) {
        int porta = 12345; 
        
        try (ServerSocket servidorSocket = new ServerSocket(porta)){
            System.out.println("Servidor aguardando conexões na porta" + porta);
            
            while (true) {
                try {
                    //verifica se o servidor aceita a conexão 
                    Socket clienteSocket = servidorSocket.accept();
                    System.out.println("Conexão aceita de" + clienteSocket.getInetAddress());
                    //cria um objeto para saida 
                    
                    Thread threadCliente = new ThreadServer(clienteSocket);
                    threadCliente.start();
                    
                } catch (IOException ex) {
                    System.out.println("Erro ao aceitar coenxão do cliente");
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro ao criar o ServerSocket");
        }
    }
}
