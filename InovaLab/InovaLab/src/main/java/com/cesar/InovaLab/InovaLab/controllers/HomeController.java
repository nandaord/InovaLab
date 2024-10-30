package com.cesar.InovaLab.InovaLab.controllers;

import com.cesar.InovaLab.InovaLab.models.UserAluno;
import com.cesar.InovaLab.InovaLab.models.UserProfessor;
import com.cesar.InovaLab.InovaLab.repository.UserAlunoRepository;
import com.cesar.InovaLab.InovaLab.repository.UserProfessorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserAlunoRepository userAlunoRepository;

    @Autowired
    private UserProfessorRepository userProfessorRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    //Mostra a página de cadastro
    @GetMapping("/cadastro")
    public String MostrarPaginaCadastro(Model model) {
        return "cadastro";
    }

    // Cadastro junto de professor e aluno
    @PostMapping("/cadastro")
    public String cadastro(@RequestParam("tipoUsuario") String tipoUsuario,
                           @RequestParam("nome") String nome,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           @RequestParam("mensagemAberta") String mensagemAberta,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        if (password.equals(confirmPassword)) { //certo
            if (userAlunoRepository.findByEmail(email).isPresent() ||
                    userProfessorRepository.findByEmail(email).isPresent()) {
                model.addAttribute("erro", "O e-mail já está cadastrado");
                return "cadastro";
            }
            else{
                if (tipoUsuario.equals("aluno")) {
                    UserAluno userAluno = new UserAluno();
                    userAluno.setNome(nome);
                    userAluno.setEmail(email);
                    userAluno.setPassword(password);
                    userAluno.setMensagemSobreVoce(mensagemAberta);
                    userAlunoRepository.save(userAluno);

                } else if (tipoUsuario.equals("professor")) {
                    UserProfessor userProfessor = new UserProfessor();
                    userProfessor.setNome(nome);
                    userProfessor.setEmail(email);
                    userProfessor.setPassword(password);
                    userProfessor.setMensagemSobreVoce(mensagemAberta);
                    userProfessorRepository.save(userProfessor);
                }
            }

            redirectAttributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso!");
            return "redirect:/";
        }
        else {
            model.addAttribute("erro", "As senhas não coincidem");
            return "cadastro";
        }
    }

    //Mostra a página de login
    @GetMapping("/login")
    public String MostrarPaginaLogin() {
        return "login";
    }

    // Login junto de aluno e professor
    @PostMapping("/login")
    public String loginUsuario(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        // Vê se é aluno
        return userAlunoRepository.findByEmail(email)
                .filter(userAluno -> userAluno.getPassword().equals(password))
                .map(userAluno -> {
                    redirectAttributes.addFlashAttribute("mensagem", "Login realizado com sucesso como Aluno");
                    return "home-aluno";
                })
                // vê se é professor
                .orElseGet(() -> userProfessorRepository.findByEmail(email)
                        .filter(userProfessor -> userProfessor.getPassword().equals(password))
                        .map(userProfessor -> {
                            redirectAttributes.addFlashAttribute("mensagem", "Login realizado com sucesso como Professor");
                            return "home-professor";
                        })
                        .orElseGet(() -> {
                            model.addAttribute("erro", "Senha ou usuário inválidos");
                            return "login";
                        })
                );
    }
}