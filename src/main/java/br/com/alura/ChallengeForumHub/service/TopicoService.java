package br.com.alura.ChallengeForumHub.service;

import br.com.alura.ChallengeForumHub.domain.Topico;
import br.com.alura.ChallengeForumHub.domain.form.TopicoForm;
import br.com.alura.ChallengeForumHub.domain.view.TopicoView;
import br.com.alura.ChallengeForumHub.repositories.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Transactional
    public TopicoView criarTopico(TopicoForm topicoForm) {
        Topico topicoParaSalvar = new Topico(topicoForm);
        Topico topico = topicoRepository.criarTopico(topicoParaSalvar);
        return new TopicoView(topico);
    }

    public TopicoView buscarTopicoPorId(Long id) {
        Topico topico = topicoRepository.buscarTopicoPorId(id);
        TopicoView topicoView = new TopicoView(topico);
        return topicoView;
    }

    public Page<TopicoView> listarTodosTopicos(String nomeCurso, Integer ano, Pageable paginacao){
        Page<Topico> topicos = topicoRepository.lintarTodosTopicos(nomeCurso, ano, paginacao);
        return topicos.map(TopicoView::new);
    }

    @Transactional
    public TopicoView atualizarTopico(Long id, TopicoForm topicoForm) {
        Topico topico = new Topico(topicoForm);
        topicoRepository.atualizarTopico(topico, id);
        TopicoView topicoView = new TopicoView(topicoRepository.buscarTopicoPorId(id));
        return topicoView;
    }

    @Transactional
    public void deletarTopico(Long id) {
        topicoRepository.deletarTopico(id);
    }
}
