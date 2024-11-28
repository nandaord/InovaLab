package com.cesar.InovaLab.InovaLab.controllers;

import com.cesar.InovaLab.InovaLab.models.UserAluno;
import com.cesar.InovaLab.InovaLab.models.UserProfessor;
import com.cesar.InovaLab.InovaLab.models.Curso;
import com.cesar.InovaLab.InovaLab.repository.UserAlunoRepository;
import com.cesar.InovaLab.InovaLab.repository.UserProfessorRepository;
import com.cesar.InovaLab.InovaLab.repository.CursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserAlunoRepository userAlunoRepository;

    @Autowired
    private UserProfessorRepository userProfessorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/home-aluno")
    public String homeAluno(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if(session.getAttribute("userType") == null || !session.getAttribute("userType").equals("aluno")){
            redirectAttributes.addFlashAttribute("erro", "Não está logado como aluno");
            return "redirect:/";
        }
        model.addAttribute("nomeUsuario", session.getAttribute("nomeUsuario"));
        return "home-aluno";
    }

    @GetMapping("/home-professor")
    public String homeProfessor(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("userType") == null || !session.getAttribute("userType").equals("professor")) {
            redirectAttributes.addFlashAttribute("erro", "Não está logado como professor");
            return "redirect:/";
        }
        model.addAttribute("nomeUsuario", session.getAttribute("nomeUsuario"));
        return "home-professor";
    }

    //Mostra a página de cadastro
    @GetMapping("/cadastro")
    public String MostrarPaginaCadastro(Model model) {
        model.addAttribute("cursos", cursoRepository.findAll());
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
                           @RequestParam("linkPortfolio") String linkPortfolio,
                           @RequestParam(value = "cursoId", required = false) Long cursoId,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        model.addAttribute("cursos", cursoRepository.findAll());

        if (password.equals(confirmPassword)) { //certo
            if (userAlunoRepository.findByEmail(email).isPresent() ||
                    userProfessorRepository.findByEmail(email).isPresent()) {
                model.addAttribute("erro", "O e-mail já está cadastrado");
                return "cadastro";
            }
            else{
                if ("aluno".equals(tipoUsuario) && cursoId != null) {
                    Curso curso = cursoRepository.findById(cursoId)
                            .orElseThrow(() -> new IllegalArgumentException("Curso inválido"));
                    UserAluno userAluno = new UserAluno();
                    userAluno.setNome(nome);
                    userAluno.setEmail(email);
                    userAluno.setPassword(password);
                    userAluno.setMensagemSobreVoce(mensagemAberta);
                    userAluno.setCurso(curso);
                    userAluno.setLinkPortifolio(linkPortfolio);
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
                               Model model,
                               HttpSession session) {
        // Vê se é aluno
        return userAlunoRepository.findByEmail(email)
                .filter(userAluno -> userAluno.getPassword().equals(password))
                .map(userAluno -> {
                    model.addAttribute("nomeUsuario", userAluno.getNome());
                    session.setAttribute("userType", "aluno");
                    session.setAttribute("nomeUsuario", userAluno.getNome()); //Bem vindo, aluno
                    session.setAttribute("descricao", userAluno.getMensagemSobreVoce());
                    session.setAttribute("curso", userAluno.getCurso());
                    session.setAttribute("alunoId", userAluno.getId());
                    //session.setAttribute("iniciativa", userAluno.iniciativa());
                    //session.setAttribute("curriculo", userAluno.getCurriculo());
                    session.setAttribute("linkPortifolio", userAluno.getLinkPortifolio());
                    return "redirect:/home-aluno";
                })
                // vê se é professor
                .orElseGet(() -> userProfessorRepository.findByEmail(email)
                        .filter(userProfessor -> userProfessor.getPassword().equals(password))
                        .map(userProfessor -> {
                            model.addAttribute("nomeUsuario", userProfessor.getNome());
                            session.setAttribute("userType", "professor");
                            session.setAttribute("nomeUsuario", userProfessor.getNome());
                            session.setAttribute("mensagemSobreVoce", userProfessor.getMensagemSobreVoce());
                            session.setAttribute("professorId", userProfessor.getId());
                            session.setAttribute("iniciativa", userProfessor.getIniciativas());
                            return "redirect:/home-professor";
                        })
                        .orElseGet(() -> {
                            model.addAttribute("erro", "Senha ou usuário inválidos");
                            return "home";
                        })
                );
    }
    //Logout
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes){
        session.invalidate();
        redirectAttributes.addFlashAttribute("mensagem", "Logout realizado com sucesso");
        return "redirect:/";
    }
}