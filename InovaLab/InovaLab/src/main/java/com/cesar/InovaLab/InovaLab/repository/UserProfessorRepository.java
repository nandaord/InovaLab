package com.cesar.InovaLab.InovaLab.repository;

import com.cesar.InovaLab.InovaLab.models.UserProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserProfessorRepository extends JpaRepository<UserProfessor, Long> {
    Optional<UserProfessor> findByUsername(String username);
    Optional<UserProfessor> findByEmail(String email);
}