package com.leozorzi.apispring.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Cliente {
@Id
@GeneratedValue (strategy = GenerationType.IDENTITY)
private Long id;

@Column (nullable = false)
    private String nome;
public Long getId() {
    return id;
    }

    public void setId(Long id) {
    this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(nome, cliente.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
