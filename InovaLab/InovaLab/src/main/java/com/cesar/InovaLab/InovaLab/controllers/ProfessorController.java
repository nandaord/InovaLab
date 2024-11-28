package com.cesar.InovaLab.InovaLab.controllers;


import com.cesar.InovaLab.InovaLab.models.Curso;
import org.springframework.web.bind.annotation.*;
import com.cesar.InovaLab.InovaLab.models.UserProfessor;
import com.cesar.InovaLab.InovaLab.repository.UserProfessorRepository;
import com.cesar.InovaLab.InovaLab.repository.CursoRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.cesar.InovaLab.InovaLab.repository.IniciativaRepository;
import com.cesar.InovaLab.InovaLab.models.Iniciativa;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home-professor")
public class ProfessorController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UserProfessorRepository userProfessorRepository;

    @Autowired
    private IniciativaRepository iniciativaRepository;

    @GetMapping("/home-professor")
    public String mostrarHomeProfessor(HttpSession session, Model model) {
        Long professorId = (Long) session.getAttribute("professorId");

        if (professorId == null) {
            return "redirect:/"; // Redireciona para login se não estiver logado
        }

        UserProfessor professor = userProfessorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        model.addAttribute("nomeUsuario", professor.getNome());

        List<Iniciativa> todasIniciativas = iniciativaRepository.findAll();
        model.addAttribute("iniciativas", todasIniciativas);

        return "home-professor"; // Nome do arquivo HTML da página inicial
    }

    @GetMapping("/perfil-professor")
    public String perfilProfessor(RedirectAttributes redirectAttributes, Model model, HttpSession session){
        Long professorId = (Long) session.getAttribute("professorId");

        if (professorId == null) {
            redirectAttributes.addFlashAttribute("erro", "Usuário não está logado.");
            return "redirect:/";
        }

        UserProfessor userProfessor = userProfessorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        model.addAttribute("nomeUsuario", userProfessor.getNome());
        model.addAttribute("userType", "Professor");
        model.addAttribute("mensagemSobreVoce", userProfessor.getMensagemSobreVoce());
        model.addAttribute("iniciativa", userProfessor.getIniciativas());

        return "perfil-professor";
    }

    @PostMapping("/excluir")
    public String excluirPerfil(RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        Long professorId = (Long) session.getAttribute("professorId");

        if(professorId != null){
            userProfessorRepository.deleteById(professorId);
            session.invalidate(); //você pode invalidar a sessão após a exclusão
            redirectAttributes.addFlashAttribute("mensagem", "Perfil excluído com sucesso!");
            return "redirect:/"; //redireciona para a home
        }else {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir o perfil.");
            return "redirect:/home-professor/perfil-professor"; //redireciona pra perfil em caso de erro
        }
    }

    @GetMapping("/editar")
    public String mostrarEditar(RedirectAttributes redirectAttributes, Model model, HttpSession session){
        Long professorId = (Long) session.getAttribute("professorId");

        if (professorId == null) {
            return "redirect:/"; // Redireciona para login se não estiver logado
        }

        // Recupera o aluno do banco de dados
        UserProfessor userProfessor = userProfessorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido"));

        model.addAttribute("professorId", userProfessor.getId());
        model.addAttribute("nomeUsuario", userProfessor.getNome());
        model.addAttribute("email", userProfessor.getEmail());
        model.addAttribute("mensagemSobreVoce", userProfessor.getMensagemSobreVoce());
        //model.addAttribute("iniciativa", userProfessor.getIniciativas());

        return "editar-perfil-professor";
    }

    @PostMapping("/editar")
    public String editarPerfil(@RequestParam("id") Long id,
                               @RequestParam("nome") String nome,
                               //@RequestParam("email") String email,
                               @RequestParam("mensagemSobreVoce") String mensagemSobreVoce,
                               RedirectAttributes redirectAttributes) {
        UserProfessor userProfessor = userProfessorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido"));

        userProfessor.setNome(nome);
        //userProfessor.setEmail(email);
        userProfessor.setMensagemSobreVoce(mensagemSobreVoce);

        userProfessorRepository.save(userProfessor);
        redirectAttributes.addFlashAttribute("mensagem", "Perfil atualizado com sucesso!");
        return "redirect:/home-professor/perfil-professor"; // Redireciona para a página do perfil após a atualização
    }

    @GetMapping("/minhas-iniciativas")
    public String mostrarMinhasIniciativas(Model model, HttpSession session) {
        Long professorId = (Long) session.getAttribute("professorId");

        if (professorId == null) {
            return "redirect:/";
        }

        UserProfessor professor = userProfessorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        model.addAttribute("iniciativas", professor.getIniciativas());
        return "minhas-iniciativas";
    }


    @GetMapping("/nova-iniciativa")
    public String mostrarNovaIniciativa(Model model) {
        List<Curso> cursos = cursoRepository.findAll();
        System.out.println("Cursos carregados: " + cursos.size());
        model.addAttribute("iniciativa", new Iniciativa());
        model.addAttribute("cursos", cursos);
        return "nova-iniciativa";
    }


    @PostMapping("/nova-iniciativa")
    public String salvarNovaIniciativa(
            @ModelAttribute Iniciativa iniciativa,
            @RequestParam("emailsAlunos") String emailsAlunos,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Long professorId = (Long) session.getAttribute("professorId");
        if (professorId == null) {
            redirectAttributes.addFlashAttribute("erro", "Usuário não está logado.");
            return "redirect:/";
        }

        UserProfessor professor = userProfessorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        // Associar professor
        iniciativa.setProfessor(professor);

        // Validar e-mails dos alunos
        if (!emailsAlunos.isBlank()) {
            List<String> emails = List.of(emailsAlunos.split(","));
            List<String> emailsValidos = new ArrayList<>();

            for (String email : emails) {
                if (userProfessorRepository.existsByEmail(email.trim())) {
                    emailsValidos.add(email.trim());
                }
            }

            iniciativa.setEmailsAlunos(emailsValidos);
        }

        iniciativaRepository.save(iniciativa);
        redirectAttributes.addFlashAttribute("mensagem", "Iniciativa criada com sucesso!");
        return "redirect:/home-professor/minhas-iniciativas";
    }

    @GetMapping("/iniciativa/{id}")
    public String getDetalhesIniciativa(@PathVariable Long id, Model model) {
        Iniciativa iniciativa = iniciativaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Iniciativa não encontrada"));
        model.addAttribute("iniciativa", iniciativa);
        return "detalhes"; // Nome do arquivo HTML
    }

    @GetMapping("/pedidos-inscricao")
    public String MostrarPedidosInscricao () {
        return "pedidos-inscricao";
    }
}