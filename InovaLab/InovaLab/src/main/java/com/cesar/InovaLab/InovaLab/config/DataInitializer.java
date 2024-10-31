package com.cesar.InovaLab.InovaLab.config;

import com.cesar.InovaLab.InovaLab.models.Curso;
import com.cesar.InovaLab.InovaLab.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.List;

// Classe para inicializar os cursos no banco de dados

@Configuration
public class DataInitializer {

    @Autowired
    private CursoRepository cursoRepository;

    @PostConstruct
    public void init() {
        if (cursoRepository.count() == 0) { // Insere apenas se não houver cursos
            List<Curso> cursos = List.of(
                    new Curso("Análise e Desenvolvimento de Sistemas (ADS)"),
                    new Curso("Banco de Dados"),
                    new Curso("Ciência da Computação"),
                    new Curso("Design"),
                    new Curso("Gestão de Tecnologia da Informação (GTI)"),
                    new Curso("Segurança da Informação"),
                    new Curso("Sistemas de Informação (SI)")
            );
            cursoRepository.saveAll(cursos);
        }
    }
}
