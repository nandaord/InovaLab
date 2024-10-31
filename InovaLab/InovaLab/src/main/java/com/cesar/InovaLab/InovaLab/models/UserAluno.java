package com.cesar.InovaLab.InovaLab.models;

import jakarta.persistence.Entity;
import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;


@Entity
public class UserAluno extends User {

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @URL
    private String linkPortifolio; // Opcional

    private boolean isParticipaIniciativa;

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getLinkPortifolio() {
        return linkPortifolio;
    }

    public void setLinkPortifolio(String linkPortifolio) {
        this.linkPortifolio = linkPortifolio;
    }

    public boolean isParticipaIniciativa() {
        return isParticipaIniciativa;
    }

    public void setParticipaIniciativa(boolean participaIniciativa) {
        this.isParticipaIniciativa = participaIniciativa;
    }
}
