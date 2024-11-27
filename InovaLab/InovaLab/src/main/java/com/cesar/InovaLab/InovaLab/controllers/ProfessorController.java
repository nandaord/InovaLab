package com.cesar.InovaLab.InovaLab.controllers;


import com.cesar.InovaLab.InovaLab.models.Curso;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.cesar.InovaLab.InovaLab.models.UserProfessor;
import com.cesar.InovaLab.InovaLab.repository.UserProfessorRepository;
import com.cesar.InovaLab.InovaLab.repository.CursoRepository;

import com.cesar.InovaLab.InovaLab.repository.IniciativaRepository;
import com.cesar.InovaLab.InovaLab.models.Iniciativa;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/perfil-professor")
    public String perfilProfessor(RedirectAttributes redirectAttributes, Model model, HttpSession session){
        Long professorId = (Long) session.getAttribute("professorId");

        if (professorId == null) {
            redirectAttributes.addFlashAttribute("erro", "Usuário não está logado.");
            return "redirect:/login";
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
            return "redirect:/login"; // Redireciona para login se não estiver logado
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
    public String mostrarMinhasIniciativas(){
        return "minhas-iniciativas";
    }

    @GetMapping("/nova-iniciativa")
    public String mostrarNovaIniciativa(Model model) {
        model.addAttribute("iniciativa", new Iniciativa());
        model.addAttribute("cursos", cursoRepository.findAll());
        return "nova-iniciativa";
    }

    @PostMapping("/nova-iniciativa")
    public String salvarNovaIniciativa(
            @ModelAttribute Iniciativa iniciativa, // Removida a vírgula extra
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // Recuperar o professor logado pelo ID armazenado na sessão
        Long professorId = (Long) session.getAttribute("professorId");

        if (professorId == null) {
            redirectAttributes.addFlashAttribute("erro", "Usuário não está logado.");
            return "redirect:/login";
        }

        // Buscar o professor no banco de dados
        UserProfessor professor = userProfessorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        // Associar o professor à iniciativa
        iniciativa.setProfessor(professor);

        // Salvar a nova iniciativa no banco de dados
        iniciativaRepository.save(iniciativa);

        // Mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Iniciativa criada com sucesso!");

        // Redirecionar para a página de iniciativas
        return "redirect:/home-professor/minhas-iniciativas";
    }
}