package com.cesar.InovaLab.InovaLab.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserAluno aluno;

    @ManyToOne
    private Iniciativa iniciativa;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;

    private LocalDateTime dataSolicitacao;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAluno getAluno() {
        return aluno;
    }

    public void setAluno(UserAluno aluno) {
        this.aluno = aluno;
    }

    public Iniciativa getIniciativa() {
        return iniciativa;
    }

    public void setIniciativa(Iniciativa iniciativa) {
        this.iniciativa = iniciativa;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }
}

