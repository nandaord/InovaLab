package com.cesar.InovaLab.InovaLab.controllers;

import com.cesar.InovaLab.InovaLab.models.User;
import com.cesar.InovaLab.InovaLab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    //Mostra a página de cadastro
    @GetMapping("/register")
    public String MostrarPaginaCadastro(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    //Cadastra de fato
    @PostMapping("/register")
    public String CadastroUser(@ModelAttribute("user") User user,
                               RedirectAttributes redirectAttributes,
                               Model model){
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            model.addAttribute("erro", "Nome de usuário já existe");
            return "register";
        }
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso!");
        return "redirect:/";
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
                            RedirectAttributes redirectAttributes,
                            Model model){
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> {
                    redirectAttributes.addFlashAttribute("mensagem", "Login realizado com sucesso");
                    return "redirect:/";
                })
                .orElseGet(() -> {
                    model.addAttribute("erro", "Senha ou usuário inválidos");
                    return "login";
                });
    }
}