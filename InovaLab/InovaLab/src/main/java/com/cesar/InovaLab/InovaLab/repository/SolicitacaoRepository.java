package com.cesar.InovaLab.InovaLab.repository;

import com.cesar.InovaLab.InovaLab.models.Solicitacao;
import com.cesar.InovaLab.InovaLab.models.Iniciativa;
import com.cesar.InovaLab.InovaLab.models.UserAluno;
import com.cesar.InovaLab.InovaLab.models.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    List<Solicitacao> findByIniciativa(Iniciativa iniciativa);
    List<Solicitacao> findByAluno(UserAluno aluno);
    Optional<Solicitacao> findByAlunoAndIniciativa(UserAluno aluno, Iniciativa iniciativa);
    List<Solicitacao> findByStatus(StatusSolicitacao status);
}
