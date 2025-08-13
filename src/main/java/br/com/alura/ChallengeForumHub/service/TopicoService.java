package br.com.alura.ChallengeForumHub.service;

import br.com.alura.ChallengeForumHub.domain.Topico;
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
        if (topico == null) {
            throw new TratadorDeErros.ResourceNotFoundException(MessageFormat.format("Tópico com o id {0} não encontrado.", id));
        }
        TopicoView topicoView = new TopicoView(topico);
        return topicoView;
    }

    public Page<TopicoView> listarTodosTopicos(String nomeCurso, Integer ano, Pageable paginacao){
        Page<Topico> topicos = topicoRepository.listarTodosTopicos(nomeCurso, ano, paginacao);
        return topicos.map(TopicoView::new);
    }

    @Transactional
    public TopicoView atualizarTopico(Long id, TopicoForm topicoForm) {
        buscarTopicoPorId(id);
        Topico topico = new Topico(topicoForm);
        topicoRepository.atualizarTopico(topico, id);
        return buscarTopicoPorId(id);
    }

    @Transactional
    public void deletarTopico(Long id) {
        buscarTopicoPorId(id);
        topicoRepository.deletarTopico(id);
    }
}
