package com.cesar.InovaLab.InovaLab.controllers;

import com.cesar.InovaLab.InovaLab.models.User;
import com.cesar.InovaLab.InovaLab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //Mostra a página de cadastro
    @GetMapping("/register")
    public String MostrarPaginaCadastro(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    //Cadastra de fato
    @PostMapping("/register")
    public String CadastroUser(@ModelAttribute("user") User user, Model model){
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            model.addAttribute("erro", "Nome de usuário já existe");
            return "register";
        }
        userRepository.save(user);
        return "redirect:/user/login";
    }

    //Mostra a página de login
    @GetMapping("/login")
    public String MostrarPaginaLogin() {
        return "login";
    }

    //Loga o usuário
    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model){
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> "redirect:/sucesso")
                .orElseGet(() -> {
                    model.addAttribute("erro", "Senha ou usuário inválidos");
                    return "login";
                });
    }
}
