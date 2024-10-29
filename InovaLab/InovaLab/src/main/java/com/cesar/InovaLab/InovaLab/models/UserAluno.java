package com.cesar.InovaLab.InovaLab.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
public class UserAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome é obrigatório")
    private String nome;

    @NotEmpty(message = "A senha é obrigatória")
    private String password;

    @NotEmpty(message = "Confirme sua senha")
    private String confirmPassword; //tem que ser implementado

    @Email // cesar
    @NotEmpty(message = "O e-mail é obrigatório")
    private String email;

    @NotEmpty(message = "Selecione seu curso")
    private String curso; //tem que ser implementado

    @URL
    private String linkPortifolio; //opcional

    private boolean isParticipaIniciativa; //tem que ser implementado

    private String mensagemSobreVoce; //tem que ser implementado


    public @Email @NotEmpty(message = "O e-mail é orbigatório") String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotEmpty(message = "O e-mail é orbigatório") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "A senha é obrigatória") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "A senha é obrigatória") String password) {
        this.password = password;
    }

    public @NotEmpty(message = "Confirme sua senha") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotEmpty(message = "Confirme sua senha") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public @NotEmpty(message = "Selecione seu curso") String getCurso() {
        return curso;
    }

    public void setCurso(@NotEmpty(message = "Selecione seu curso") String curso) {
        this.curso = curso;
    }

    public @NotEmpty(message = "O nome é obrigatório") String getNome() {
        return nome;
    }

    public void setNome(@NotEmpty(message = "O nome é obrigatório") String nome) {
        this.nome = nome;
    }

    public String getMensagemSobreVoce() {
        return mensagemSobreVoce;
    }

    public void setMensagemSobreVoce(String mensagemSobreVoce) {
        this.mensagemSobreVoce = mensagemSobreVoce;
    }

    public @URL String getLinkPortifolio() {
        return linkPortifolio;
    }

    public void setLinkPortifolio(@URL String linkPortifolio) {
        this.linkPortifolio = linkPortifolio;
    }

    public boolean isParticipaIniciativa() {
        return isParticipaIniciativa;
    }

    public void setParticipaIniciativa(boolean participaIniciativa) {
        isParticipaIniciativa = participaIniciativa;
    }
}