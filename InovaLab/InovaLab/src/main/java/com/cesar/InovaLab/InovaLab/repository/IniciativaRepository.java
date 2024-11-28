package com.cesar.InovaLab.InovaLab.repository;

import com.cesar.InovaLab.InovaLab.models.UserAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cesar.InovaLab.InovaLab.models.Iniciativa;// Importe a classe Iniciativa corretamente

import java.util.List;

public interface IniciativaRepository extends JpaRepository<Iniciativa, Long> {

}
