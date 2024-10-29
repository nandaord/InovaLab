package com.cesar.InovaLab.InovaLab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SucessoController {

    @GetMapping("/sucesso")
    public String home() {
        return "sucesso"; // Retorna a view da página inicial
    }
}
