package com.cesar.InovaLab.InovaLab.controllers;

import com.cesar.InovaLab.InovaLab.models.Iniciativa;
import com.cesar.InovaLab.InovaLab.models.UserProfessor;
import com.cesar.InovaLab.InovaLab.models.UserAluno;
import com.cesar.InovaLab.InovaLab.repository.IniciativaRepository;
import com.cesar.InovaLab.InovaLab.repository.UserAlunoRepository;
import com.cesar.InovaLab.InovaLab.repository.UserProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

import java.security.Principal;

@Controller
@RequestMapping("/home-professor")
public class IniciativaController {

    @Autowired
    private IniciativaRepository iniciativaRepository;

    @Autowired
    private UserAlunoRepository userAlunoRepository;

    // Método para carregar os dados da iniciativa na página de edição
    @GetMapping("/editar-iniciativa/{id}")
    public String editarIniciativa(@PathVariable Long id, Model model) {
        Iniciativa iniciativa = iniciativaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Iniciativa inválida"));
        List<UserAluno> alunos = userAlunoRepository.findByIniciativaId(id); // Recupera os alunos associados à iniciativa
        model.addAttribute("iniciativa", iniciativa);
        model.addAttribute("alunos", alunos); // Passa os alunos para a view
        return "editarIniciativa";
    }

    // Método para processar a edição da iniciativa
    @PostMapping("/editar-iniciativa/{id}")
    public String salvarIniciativaEditada(@PathVariable("id") Long id,
                                          @RequestParam("titulo") String titulo,
                                          @RequestParam("descricao") String descricao,
                                          RedirectAttributes redirectAttributes) {
        // Encontra a iniciativa existente
        Iniciativa iniciativa = iniciativaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Iniciativa inválida"));

        // Atualiza os dados da iniciativa
        iniciativa.setTitulo(titulo);
        iniciativa.setDescricao(descricao);

        // Salva as alterações
        iniciativaRepository.save(iniciativa);

        // Adiciona mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Iniciativa editada com sucesso!");
        return "redirect:/home-professor"; // Ou para a página desejada
    }

    @PostMapping("/remover-aluno/{iniciativaId}/{alunoId}")
    public String removerAluno(@PathVariable("iniciativaId") Long iniciativaId,
                               @PathVariable("alunoId") Long alunoId,
                               RedirectAttributes redirectAttributes) {
        // Buscar a iniciativa
        Iniciativa iniciativa = iniciativaRepository.findById(iniciativaId)
                .orElseThrow(() -> new IllegalArgumentException("Iniciativa inválida"));

        // Buscar o aluno na lista de associados
        UserAluno aluno = userAlunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno inválido"));

        // Remover aluno da lista de associados
        iniciativa.getAlunosAssociados().remove(aluno);

        // Salvar a iniciativa atualizada
        iniciativaRepository.save(iniciativa);

        // Redirecionar com mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Aluno removido com sucesso!");
        return "redirect:/home-professor/editar-iniciativa/" + iniciativaId;
    }


}
