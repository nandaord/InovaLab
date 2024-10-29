package com.cesar.InovaLab.InovaLab.controllers;

import com.cesar.InovaLab.InovaLab.models.UserAluno;
import com.cesar.InovaLab.InovaLab.repository.UserAlunoRepository;
import com.cesar.InovaLab.InovaLab.models.UserProfessor;
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

    //Mostra a página de cadastro aluno
    @GetMapping("/cadastro-aluno")
    public String MostrarPaginaCadastroAluno(Model model) {
        model.addAttribute("user-aluno", new UserAluno());
        return "cadastro-aluno";
    }

    //Cadastra aluno de fato
    @PostMapping("/cadastro-aluno")
    public String CadastroUserAluno(@ModelAttribute("user-aluno") UserAluno userAluno,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        if (userAlunoRepository.findByEmail(userAluno.getEmail()).isPresent()) {
            model.addAttribute("erro", "O Email já está cadastrado");
            return "cadastro-aluno";
        }
        if (!userAluno.getPassword().equals(userAluno.getConfirmPassword())) {
            model.addAttribute("erro", "As senhas não coincidem");
            return "cadastro-aluno";
        }
        userAlunoRepository.save(userAluno);
        redirectAttributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso!");
        return "redirect:/";
    }

    //Mostra a página de login aluno
    @GetMapping("/login-aluno")
    public String MostrarPaginaLoginAluno() {
        return "login-aluno";
    }

    //Loga o usuário
    @PostMapping("/login-aluno")
    public String loginUserAluno(@RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        return userAlunoRepository.findByEmail(email)
                .filter(userAluno -> userAluno.getPassword().equals(password))
                .map(userAluno -> {
                    redirectAttributes.addFlashAttribute("mensagem", "Login realizado com sucesso");
                    return "redirect:/";
                })
                .orElseGet(() -> {
                    model.addAttribute("erro", "Senha ou usuário inválidos");
                    return "login-aluno";
                });
    }

    //Mostra a página de cadastro professor
    @GetMapping("/cadastro-professor")
    public String MostrarPaginaCadastroProfessor(Model model) {
        model.addAttribute("user-professor", new UserProfessor());
        return "cadastro-professor";
    }

    //Cadastra professor de fato
    @PostMapping("/cadastro-professor")
    public String CadastroUserProfessor(@ModelAttribute("user-professor") UserProfessor userProfessor,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {
        if (userProfessorRepository.findByEmail(userProfessor.getEmail()).isPresent()) {
            model.addAttribute("erro", "O Email já está cadastrado");
            return "cadastro-professor";
        }
        if (!userProfessor.getPassword().equals(userProfessor.getConfirmPassword())) {
            model.addAttribute("erro", "As senhas não coincidem");
            return "cadastro-professor";
        }
        userProfessorRepository.save(userProfessor);
        redirectAttributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso!");
        return "redirect:/";
    }

    //Mostra a página de login professor
    @GetMapping("/login-professor")
    public String MostrarPaginaLoginProfessor() {
        return "login-professor";
    }

    //Loga o usuário
    @PostMapping("/login-professor")
    public String loginUserProfessor(@RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        return userProfessorRepository.findByEmail(email)
                .filter(userProfessor -> userProfessor.getPassword().equals(password))
                .map(userProfessor -> {
                    redirectAttributes.addFlashAttribute("mensagem", "Login realizado com sucesso");
                    return "redirect:/";
                })
                .orElseGet(() -> {
                    model.addAttribute("erro", "Senha ou usuário inválidos");
                    return "login-professor";
                });
    }
}