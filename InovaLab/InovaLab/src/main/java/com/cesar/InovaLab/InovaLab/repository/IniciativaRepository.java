package com.cesar.InovaLab.InovaLab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cesar.InovaLab.InovaLab.models.Iniciativa;// Importe a classe Iniciativa corretamente

public interface IniciativaRepository extends JpaRepository<Iniciativa, Long> {
    // Métodos adicionais de consulta podem ser definidos aqui, se necessário
}
