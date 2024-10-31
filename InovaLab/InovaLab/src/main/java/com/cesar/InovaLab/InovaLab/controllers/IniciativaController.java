package com.cesar.InovaLab.InovaLab.controllers;

import com.cesar.InovaLab.InovaLab.models.Iniciativa;
import com.cesar.InovaLab.InovaLab.models.UserProfessor;
import com.cesar.InovaLab.InovaLab.repository.IniciativaRepository;
import com.cesar.InovaLab.InovaLab.repository.UserProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/iniciativas")
public class IniciativaController {

    @Autowired
    private IniciativaRepository iniciativaRepository;

    @Autowired
    private UserProfessorRepository userProfessorRepository;

    // Exibe o formulário de criação de iniciativas
    @GetMapping("/nova")
    public String novaIniciativa(Model model) {
        model.addAttribute("iniciativa", new Iniciativa());
        return "nova-iniciativa"; // A view que contém o formulário
    }

    // Salva a nova iniciativa
    @PostMapping("/nova")
    public String criarIniciativa(@ModelAttribute Iniciativa iniciativa, Principal principal) {
        UserProfessor professor = userProfessorRepository.findByEmail(principal.getName()).orElseThrow();
        iniciativa.setProfessor(professor);
        iniciativaRepository.save(iniciativa);
        return "redirect:/iniciativas/minhas"; // Redireciona para a página das iniciativas do professor
    }

    // Exibe as iniciativas do professor
    @GetMapping("/minhas")
    public String minhasIniciativas(Model model, Principal principal) {
        UserProfessor professor = userProfessorRepository.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("iniciativas", professor.getIniciativas());
        return "minhas-iniciativas"; // A view que lista as iniciativas do professor
    }
}
