package br.com.alura.ChallengeForumHub.service;

import br.com.alura.ChallengeForumHub.domain.Topico;
import br.com.alura.ChallengeForumHub.domain.Usuario;
import br.com.alura.ChallengeForumHub.domain.dto.form.TopicoForm;
import br.com.alura.ChallengeForumHub.domain.dto.view.TopicoView;
import br.com.alura.ChallengeForumHub.exception.TratadorDeErros;
import br.com.alura.ChallengeForumHub.repositories.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Objects;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public TopicoView criarTopico(TopicoForm topicoForm) {
        Usuario usuario = usuarioService.getUsuarioLogado();
        Topico topicoParaSalvar = new Topico(topicoForm).withAutorId(usuario.getId());
        Topico topico = topicoRepository.criarTopico(topicoParaSalvar);
        return new TopicoView(topico);
    }

    public TopicoView buscarTopicoPorId(Long id) {
        Topico topico = buscarEntidadeTopicoPorId(id);
        TopicoView topicoView = new TopicoView(topico);
        return topicoView;
    }

    public Page<TopicoView> listarTodosTopicos(String nomeCurso, Integer ano, Pageable paginacao){
        Page<Topico> topicos = topicoRepository.listarTodosTopicos(nomeCurso, ano, paginacao);
        return topicos.map(TopicoView::new);
    }

    @Transactional
    public TopicoView atualizarTopico(Long id, TopicoForm topicoForm) {
        Topico topico = buscarEntidadeTopicoPorId(id);
        verificarAutoriaTopico(topico);
        Topico topicoAtualizado = new Topico(topicoForm);
        topicoRepository.atualizarTopico(topicoAtualizado, id);
        return buscarTopicoPorId(id);
    }

    @Transactional
    public void deletarTopico(Long id) {
        Topico topico = buscarEntidadeTopicoPorId(id);
        verificarAutoriaTopico(topico);
        topicoRepository.deletarTopico(id);
    }

    private void verificarAutoriaTopico(Topico topico) {
        Usuario usuario = usuarioService.getUsuarioLogado();
        if (!Objects.equals(topico.getAutorId(), usuario.getId())) {
            throw new TratadorDeErros.ResourceNotFoundException("Este Tópico não é do Usuário Logado! Não pode ser Alterado");
        }
    }

    private Topico buscarEntidadeTopicoPorId(Long id) {
        Topico topico = topicoRepository.buscarTopicoPorId(id);
        if (topico == null) {
            throw new TratadorDeErros.ResourceNotFoundException(MessageFormat.format("Tópico com o id {0} não encontrado.", id));
        }
        return topico;
    }
}
