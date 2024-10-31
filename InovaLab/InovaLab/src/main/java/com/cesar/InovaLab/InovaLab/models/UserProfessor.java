package com.cesar.InovaLab.InovaLab.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class UserProfessor extends User {

    @OneToMany(mappedBy = "professor")
    private List<Iniciativa> iniciativas;

    public List<Iniciativa> getIniciativas() {
        return iniciativas;
    }

    public void setIniciativas(List<Iniciativa> iniciativas) {
        this.iniciativas = iniciativas;
    }
}
