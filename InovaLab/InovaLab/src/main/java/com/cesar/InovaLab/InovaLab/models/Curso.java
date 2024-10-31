package com.cesar.InovaLab.InovaLab.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nome;

    public Curso() {}

    public Curso(String nome) {
        this.nome = nome;
    }

    public @NotEmpty String getNome() {
        return nome;
    }

    public void setNome(@NotEmpty String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }
}
