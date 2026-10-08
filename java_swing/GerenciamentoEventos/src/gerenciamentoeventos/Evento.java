/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gerenciamentoeventos;

/**
 *
 * @author laboratorio
 */
public class Evento {
        
    public String nomeEvento;
    public String data;
    public String local;
    public String tipo;
    public String modalidade;
    public String situacao;
    
    public Evento(String nomeEvento, String data, String local, String tipo, String modalidade, String situacao) {
        this.nomeEvento = nomeEvento;
        this.data = data;
        this.local = local;
        this.tipo = tipo;
        this.modalidade = modalidade;
        this.situacao = situacao;
    }

    
    @Override
    public String toString() {
        return "Evento{" + "nomeEvento=" + nomeEvento + ", data=" + data + ", local=" + local + ", tipo=" + tipo + ", modalidade=" + modalidade + ", situacao=" + situacao + '}';
    }
    public Object[] ObterDados(){
         return new Object[] {nomeEvento, data, local, tipo, modalidade, situacao};
    }
}

