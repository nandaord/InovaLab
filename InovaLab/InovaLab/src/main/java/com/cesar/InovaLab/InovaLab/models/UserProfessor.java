package com.cesar.InovaLab.InovaLab.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class UserProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome é obrigatório")
    private String nome;

    @NotEmpty(message = "A senha é obrigatória")
    private String password;

    @NotEmpty(message = "Confirme sua senha")
    private String confirmPassword; //tem que ser implementado

    @Email //cesar
    @NotEmpty(message = "O e-mail é obrigatório")
    private String email;

    private String mensagemSobreVoce; //opcional

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

    public @NotEmpty(message = "Confirme sua senha") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotEmpty(message = "Confirme sua senha") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
