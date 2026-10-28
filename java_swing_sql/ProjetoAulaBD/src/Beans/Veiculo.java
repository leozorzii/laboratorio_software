/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Beans;

/**
 *
 * @author leo zorzi
 */
public class Veiculo {
    private int id;
    private String placa;
    private String modelo;
    private Pessoa pessoaid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Pessoa getPessoaid() {
        return pessoaid;
    }

    public void setPessoaid(Pessoa pessoaid) {
        this.pessoaid = pessoaid;
    }
}
