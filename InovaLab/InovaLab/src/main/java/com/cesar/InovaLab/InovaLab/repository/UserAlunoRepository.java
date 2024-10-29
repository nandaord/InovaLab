package com.cesar.InovaLab.InovaLab.repository;

import com.cesar.InovaLab.InovaLab.models.UserAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserAlunoRepository extends JpaRepository<UserAluno, Long> {
    Optional<UserAluno> findByUsername(String username);
    Optional<UserAluno> findByEmail(String email);
}