package com.cesar.InovaLab.InovaLab.repository;

import com.cesar.InovaLab.InovaLab.models.Iniciativa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IniciativaRepository extends JpaRepository<Iniciativa, Long> {
    // Você pode adicionar métodos personalizados aqui, se necessário
}
