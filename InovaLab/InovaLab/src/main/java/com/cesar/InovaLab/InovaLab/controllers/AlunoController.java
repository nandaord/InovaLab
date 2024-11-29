package com.cesar.InovaLab.InovaLab.controllers;

import com.cesar.InovaLab.InovaLab.models.Iniciativa;
import com.cesar.InovaLab.InovaLab.models.UserAluno;
import com.cesar.InovaLab.InovaLab.repository.UserAlunoRepository;
import com.cesar.InovaLab.InovaLab.repository.CursoRepository;
import com.cesar.InovaLab.InovaLab.repository.IniciativaRepository;
import com.cesar.InovaLab.InovaLab.repository.SolicitacaoRepository;
import com.cesar.InovaLab.InovaLab.models.Curso;
import com.cesar.InovaLab.InovaLab.models.Solicitacao;
import com.cesar.InovaLab.InovaLab.models.StatusSolicitacao;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home-aluno")
public class AlunoController {

    @Autowired
    private IniciativaRepository iniciativaRepository;

    @Autowired
    private UserAlunoRepository userAlunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @GetMapping("/perfil-aluno")
    public String perfil(RedirectAttributes redirectAttributes, Model model, HttpSession session) {

        //recebe o id do aluno lá do login em HomeController
        Long alunoId = (Long) session.getAttribute("alunoId");

        //se o aluno não tiver logado
        if (alunoId == null) {
            redirectAttributes.addFlashAttribute("erro", "Usuário não está logado.");
            return "redirect:/";
        }

        //verifica se o id do aluno da cadastrado no banco de dados
        UserAluno userAluno = userAlunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        model.addAttribute("nomeUsuario", userAluno.getNome());
        model.addAttribute("userType", "Aluno");
        model.addAttribute("curso", userAluno.getCurso().getNome());
        model.addAttribute("descricao", userAluno.getMensagemSobreVoce());
        model.addAttribute("linkPortifolio", userAluno.getLinkPortifolio());

        return "perfil-aluno";
    }

    @PostMapping("/excluir")
    public String excluirPerfil(HttpSession session, RedirectAttributes redirectAttributes) {
        Long alunoId = (Long) session.getAttribute("alunoId");

        if (alunoId != null) {
            userAlunoRepository.deleteById(alunoId);
            session.invalidate(); //você pode invalidar a sessão após a exclusão
            redirectAttributes.addFlashAttribute("mensagem", "Perfil excluído com sucesso!");
            return "redirect:/"; //redireciona para a home
        } else {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir o perfil.");
            return "redirect:/home-aluno/perfil-aluno"; //redireciona pra perfil em caso de erro
        }
    }

    //mostrar a pagina
    @GetMapping("/editar-perfil-aluno")
    public String mostrarPaginaEdicao(HttpSession session, Model model) {
        Long alunoId = (Long) session.getAttribute("alunoId");
        if (alunoId == null) {
            return "redirect:/"; // Redireciona para login se não estiver logado
        }

        // Recupera o aluno do banco de dados
        UserAluno userAluno = userAlunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido"));

        model.addAttribute("alunoId", userAluno.getId());
        model.addAttribute("nomeUsuario", userAluno.getNome());
        model.addAttribute("email", userAluno.getEmail());
        model.addAttribute("descricao", userAluno.getMensagemSobreVoce());
        model.addAttribute("linkPortifolio", userAluno.getLinkPortifolio());
        model.addAttribute("cursoId", userAluno.getCurso().getId());
        model.addAttribute("cursos", cursoRepository.findAll());

        return "editar-perfil-aluno";
    }

    //editar perfil
    @PostMapping("/editar-perfil-aluno")
    public String editarPerfil(@RequestParam("id") Long id,
                               @RequestParam("nome") String nome,
                               //@RequestParam("email") String email,
                               @RequestParam("descricao") String descricao,
                               @RequestParam("linkPortfolio") String linkPortfolio,
                               @RequestParam("cursoId") Long cursoId,
                               RedirectAttributes redirectAttributes) {
        UserAluno userAluno = userAlunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido"));

        userAluno.setNome(nome);
        //userAluno.setEmail(email);
        userAluno.setMensagemSobreVoce(descricao);
        userAluno.setLinkPortifolio(linkPortfolio);

        // Atualiza o curso, se necessário
        if (cursoId != null) {
            Curso curso = cursoRepository.findById(cursoId)
                    .orElseThrow(() -> new IllegalArgumentException("Curso inválido"));
            userAluno.setCurso(curso);
        }

        userAlunoRepository.save(userAluno);
        redirectAttributes.addFlashAttribute("mensagem", "Perfil atualizado com sucesso!");
        return "redirect:/home-aluno/perfil-aluno"; // Redireciona para a página do perfil após a atualização
    }

    @GetMapping("/inscricoes-abertas")
    public String mostrarIniciativasAbertas(Model model) {
        // Buscar as iniciativas com inscrições abertas
        List<Iniciativa> iniciativasAbertas = iniciativaRepository.findByAceitaInscricoesTrue();

        // Adicionar as iniciativas ao modelo
        model.addAttribute("iniciativas", iniciativasAbertas);

        return "inscricoes-abertas";  // Nome do template Thymeleaf
    }

    @GetMapping("/iniciativa/{id}")
    public String getDetalhesIniciativa(@PathVariable Long id,
                                        @RequestParam String userType, // Recebe tipo do usuário como parâmetro
                                        Model model) {
        // Busca a iniciativa no banco de dados
        Iniciativa iniciativa = iniciativaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Iniciativa não encontrada"));
        model.addAttribute("iniciativa", iniciativa);

        // Verifica o tipo de usuário e redireciona para a página correta
        if ("professor".equalsIgnoreCase(userType)) {
            return "detalhes"; // Página de detalhes para professor
        } else if ("aluno".equalsIgnoreCase(userType)) {
            return "detalhes-aluno"; // Página de detalhes para aluno
        }

        // Caso o tipo não seja reconhecido, lança exceção
        throw new IllegalArgumentException("Tipo de usuário inválido");
    }


    @PostMapping("/iniciativa/{id}/inscrever")
    public String inscreverNaIniciativa(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Long alunoId = (Long) session.getAttribute("alunoId");

        if (alunoId == null) {
            redirectAttributes.addFlashAttribute("erro", "Usuário não está logado.");
            return "redirect:/login";  // redirecionar para login, se não estiver logado
        }

        UserAluno aluno = userAlunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        Iniciativa iniciativa = iniciativaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Iniciativa não encontrada"));

        // Verifica se já existe uma solicitação pendente para essa iniciativa
        Optional<Solicitacao> solicitacaoExistente = solicitacaoRepository.findByAlunoAndIniciativa(aluno, iniciativa);
        if (solicitacaoExistente.isPresent()) {
            redirectAttributes.addFlashAttribute("erro", "Você já enviou uma solicitação para esta iniciativa.");
            return "redirect:/home-aluno/inscricoes-abertas";
        }

        // Cria uma nova solicitação
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setAluno(aluno);
        solicitacao.setIniciativa(iniciativa);
        solicitacao.setStatus(StatusSolicitacao.PENDENTE);
        solicitacao.setDataSolicitacao(LocalDateTime.now());
        solicitacaoRepository.save(solicitacao);

        redirectAttributes.addFlashAttribute("mensagem", "Solicitação enviada com sucesso!");
        return "redirect:/home-aluno/inscricoes-abertas";
    }

}