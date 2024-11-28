package com.cesar.InovaLab.InovaLab.repository;

import com.cesar.InovaLab.InovaLab.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cesar.InovaLab.InovaLab.models.UserAluno;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    public List<UserAluno> findByIniciativaId(Long iniciativaId);
}
