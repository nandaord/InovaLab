package com.cesar.InovaLab.InovaLab.controllers;


import com.cesar.InovaLab.InovaLab.models.Curso;
import com.cesar.InovaLab.InovaLab.models.Solicitacao;
import com.cesar.InovaLab.InovaLab.models.StatusSolicitacao;
import com.cesar.InovaLab.InovaLab.models.UserAluno;
import com.cesar.InovaLab.InovaLab.repository.*;
import org.springframework.web.bind.annotation.*;
import com.cesar.InovaLab.InovaLab.models.UserProfessor;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.cesar.InovaLab.InovaLab.models.Iniciativa;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home-professor")
public class ProfessorController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UserProfessorRepository userProfessorRepository;

    @Autowired
    private IniciativaRepository iniciativaRepository;

    @Autowired
    private UserAlunoRepository userAlunoRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @GetMapping("/home-professor")
    public String mostrarHomeProfessor(HttpSession session, Model model) {
        Long professorId = (Long) session.getAttribute("professorId");

        if (professorId == null) {
            return "redirect:/"; // Redireciona para login se não estiver logado
        }

        UserProfessor professor = userProfessorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        model.addAttribute("nomeUsuario", professor.getNome());

        List<Iniciativa> todasIniciativas = iniciativaRepository.findAll();
        model.addAttribute("iniciativas", todasIniciativas);

        return "home-professor"; // Nome do arquivo HTML da página inicial
    }

    @GetMapping("/perfil-professor")
    public String perfilProfessor(RedirectAttributes redirectAttributes, Model model, HttpSession session){
        Long professorId = (Long) session.getAttribute("professorId");

        if (professorId == null) {
            redirectAttributes.addFlashAttribute("erro", "Usuário não está logado.");
            return "redirect:/";
        }

        UserProfessor userProfessor = userProfessorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        model.addAttribute("nomeUsuario", userProfessor.getNome());
        model.addAttribute("userType", "Professor");
        model.addAttribute("mensagemSobreVoce", userProfessor.getMensagemSobreVoce());
        model.addAttribute("iniciativa", userProfessor.getIniciativas());

        return "perfil-professor";
    }

    @PostMapping("/excluir")
    public String excluirPerfil(RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        Long professorId = (Long) session.getAttribute("professorId");

        if(professorId != null){
            userProfessorRepository.deleteById(professorId);
            session.invalidate(); //você pode invalidar a sessão após a exclusão
            redirectAttributes.addFlashAttribute("mensagem", "Perfil excluído com sucesso!");
            return "redirect:/"; //redireciona para a home
        }else {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir o perfil.");
            return "redirect:/home-professor/perfil-professor"; //redireciona pra perfil em caso de erro
        }
    }

    @GetMapping("/editar")
    public String mostrarEditar(RedirectAttributes redirectAttributes, Model model, HttpSession session){
        Long professorId = (Long) session.getAttribute("professorId");

        if (professorId == null) {
            return "redirect:/"; // Redireciona para login se não estiver logado
        }

        // Recupera o aluno do banco de dados
        UserProfessor userProfessor = userProfessorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido"));

        model.addAttribute("professorId", userProfessor.getId());
        model.addAttribute("nomeUsuario", userProfessor.getNome());
        model.addAttribute("email", userProfessor.getEmail());
        model.addAttribute("mensagemSobreVoce", userProfessor.getMensagemSobreVoce());
        //model.addAttribute("iniciativa", userProfessor.getIniciativas());

        return "editar-perfil-professor";
    }

    @PostMapping("/editar")
    public String editarPerfil(@RequestParam("id") Long id,
                               @RequestParam("nome") String nome,
                               //@RequestParam("email") String email,
                               @RequestParam("mensagemSobreVoce") String mensagemSobreVoce,
                               RedirectAttributes redirectAttributes) {
        UserProfessor userProfessor = userProfessorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido"));

        userProfessor.setNome(nome);
        //userProfessor.setEmail(email);
        userProfessor.setMensagemSobreVoce(mensagemSobreVoce);

        userProfessorRepository.save(userProfessor);
        redirectAttributes.addFlashAttribute("mensagem", "Perfil atualizado com sucesso!");
        return "redirect:/home-professor/perfil-professor"; // Redireciona para a página do perfil após a atualização
    }

    @GetMapping("/minhas-iniciativas")
    public String mostrarMinhasIniciativas(Model model, HttpSession session) {
        Long professorId = (Long) session.getAttribute("professorId");

        if (professorId == null) {
            return "redirect:/";
        }

        UserProfessor professor = userProfessorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        model.addAttribute("iniciativas", professor.getIniciativas());
        return "minhas-iniciativas";
    }


    @GetMapping("/nova-iniciativa")
    public String mostrarNovaIniciativa(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Verifica se o professor está logado
        Long professorId = (Long) session.getAttribute("professorId");
        if (professorId == null) {
            redirectAttributes.addFlashAttribute("erro", "Usuário não está logado.");
            return "redirect:/"; // Redireciona para a página inicial ou de login
        }

        // Carrega os cursos do banco de dados
        List<Curso> cursos = cursoRepository.findAll();

        // Carrega os alunos do banco de dados
        List<UserAluno> alunos = userAlunoRepository.findAll();

        // Debug para verificar os cursos e alunos carregados
        System.out.println("Cursos carregados: " + cursos.size());
        System.out.println("Alunos carregados: " + alunos.size());

        // Adiciona os atributos ao modelo para exibir no formulário
        model.addAttribute("iniciativa", new Iniciativa());
        model.addAttribute("cursos", cursos);
        model.addAttribute("alunos", alunos);

        // Retorna a página do formulário (substitua "nova-iniciativa" pelo nome do HTML real)
        return "nova-iniciativa";
    }

    @PostMapping("/nova-iniciativa")
    public String salvarNovaIniciativa(
            @ModelAttribute Iniciativa iniciativa,
            @RequestParam("emailsAlunos") List<String> emailsAlunos,  // Recebe uma lista de e-mails agora
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Long professorId = (Long) session.getAttribute("professorId");
        if (professorId == null) {
            redirectAttributes.addFlashAttribute("erro", "Usuário não está logado.");
            return "redirect:/";
        }

        // Encontrar o professor pelo ID da sessão
        UserProfessor professor = userProfessorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        // Associar professor à iniciativa
        iniciativa.setProfessor(professor);

        // Validar e-mails dos alunos
        if (emailsAlunos != null && !emailsAlunos.isEmpty()) {
            List<String> emailsValidos = new ArrayList<>();

            // Verificar se o e-mail existe no banco de dados de alunos
            for (String email : emailsAlunos) {
                if (userAlunoRepository.existsByEmail(email.trim())) {
                    emailsValidos.add(email.trim());
                }
            }

            // Atribuir a lista de e-mails válidos à iniciativa
            iniciativa.setEmailsAlunos(emailsValidos);
        }

        // Salvar a iniciativa no banco de dados
        iniciativaRepository.save(iniciativa);

        // Adicionar mensagem de sucesso e redirecionar para a página de iniciativas do professor
        redirectAttributes.addFlashAttribute("mensagem", "Iniciativa criada com sucesso!");
        return "redirect:/home-professor/minhas-iniciativas";
    }


    @GetMapping("/iniciativa/{id}")
    public String getDetalhesIniciativa(@PathVariable Long id, Model model, HttpSession session) {
        Iniciativa iniciativa = iniciativaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Iniciativa não encontrada"));
        String userType = (String) session.getAttribute("userType");

        model.addAttribute("iniciativa", iniciativa);
        model.addAttribute("userType", userType);  // Passando o userType para o HTML
        return "detalhes"; // Nome do arquivo HTML
    }

    @GetMapping("/home-professor/pedidos-inscricao")
    public String visualizarPedidosInscricao(Model model) {
        List<Solicitacao> solicitacoes = solicitacaoRepository.findByStatus(StatusSolicitacao.PENDENTE);  // Filtrando por status pendente
        model.addAttribute("solicitacoes", solicitacoes);
        return "pedidos-inscricao";
    }

    @PostMapping("/pedidos-inscricao/aceitar/{id}")
    public String aceitarSolicitacao(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));

        solicitacao.setStatus(StatusSolicitacao.ACEITA);
        solicitacaoRepository.save(solicitacao);

        redirectAttributes.addFlashAttribute("mensagem", "Solicitação aceita com sucesso.");
        return "redirect:/home-professor/pedidos-inscricao";
    }

    @PostMapping("/pedidos-inscricao/rejeitar/{id}")
    public String rejeitarSolicitacao(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));

        solicitacao.setStatus(StatusSolicitacao.REJEITADA);
        solicitacaoRepository.save(solicitacao);

        redirectAttributes.addFlashAttribute("erro", "Solicitação rejeitada.");
        return "redirect:/home-professor/pedidos-inscricao";
    }
}