package com.cesar.InovaLab.InovaLab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cesar.InovaLab.InovaLab.models.Iniciativa;// Importe a classe Iniciativa corretamente

import java.util.List;

public interface IniciativaRepository extends JpaRepository<Iniciativa, Long> {
    List<Iniciativa> findByAceitaInscricoesTrue();
    List<Iniciativa> findByEmailsAlunos(String email);
}