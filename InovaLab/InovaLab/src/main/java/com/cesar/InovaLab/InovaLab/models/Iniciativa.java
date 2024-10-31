package com.cesar.InovaLab.InovaLab.models;

import jakarta.persistence.*;

@Entity
public class Iniciativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private UserProfessor professor;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UserProfessor getProfessor() {
        return professor;
    }

    public void setProfessor(UserProfessor professor) {
        this.professor = professor;
    }
}
