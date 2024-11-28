package com.cesar.InovaLab.InovaLab.models;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@Entity
public class Iniciativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O título é obrigatório.")
    private String titulo;

    @NotNull(message = "A descrição é obrigatória.")
    private String descricao;

    private boolean aceitaInscricoes;

    @ManyToMany
    @JoinTable(
            name = "iniciativa_curso",
            joinColumns = @JoinColumn(name = "iniciativa_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursosPermitidos;

    @Future(message = "A data deve ser no futuro.")
    private LocalDate dataLimiteInscricoes;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private UserProfessor professor;

    public boolean isAceitaInscricoes() {
        return aceitaInscricoes;
    }

    public void setAceitaInscricoes(boolean aceitaInscricoes) {
        this.aceitaInscricoes = aceitaInscricoes;
    }

    public @Future(message = "A data deve ser no futuro.") LocalDate getDataLimiteInscricoes() {
        return dataLimiteInscricoes;
    }

    public void setDataLimiteInscricoes(@Future(message = "A data deve ser no futuro.") LocalDate dataLimiteInscricoes) {
        this.dataLimiteInscricoes = dataLimiteInscricoes;
    }

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