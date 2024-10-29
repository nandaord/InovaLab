package com.cesar.InovaLab.InovaLab.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome de usuário é obrigatório")
    private String username;

    @NotEmpty(message = "A senha é obrigatória")
    private String password;

    @Email
    @NotEmpty(message = "O e-mail é obrigatório")
    private String email;

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

    public @NotEmpty(message = "O nome de usuário é obrigatório") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "O nome de usuário é obrigatório") String username) {
        this.username = username;
    }
}