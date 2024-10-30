package com.cesar.InovaLab.InovaLab.models;

import jakarta.persistence.Entity;
import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class UserAluno extends User {

    private String curso;

    @URL
    private String linkPortifolio; // Opcional

    private boolean isParticipaIniciativa;

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
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
