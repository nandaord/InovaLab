package com.cesar.InovaLab.InovaLab.repository;

import com.cesar.InovaLab.InovaLab.models.UserAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserAlunoRepository extends JpaRepository<UserAluno, Long> {
    Optional<UserAluno> findByEmail(String email);
    boolean existsByEmail(String email);
}