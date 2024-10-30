package com.cesar.InovaLab.InovaLab.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

//VAI SER HERDADA DE ALUNO E PROFESSOR ADICIONANDO LÁ OS ATRIBUTOS QUE DIFERENCIAM ENTRE SI
//AQUI TEM SÓ OS ATRIBUTOS QUE COINCIDEM ENTRE SI
@Entity
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome é obrigatório")
    private String nome;

    @NotEmpty(message = "A senha é obrigatória")
    private String password;

    @Email
    @NotEmpty(message = "O e-mail é obrigatório")
    private String email;

    private String mensagemSobreVoce; // Opcional

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensagemSobreVoce() {
        return mensagemSobreVoce;
    }

    public void setMensagemSobreVoce(String mensagemSobreVoce) {
        this.mensagemSobreVoce = mensagemSobreVoce;
    }
}
