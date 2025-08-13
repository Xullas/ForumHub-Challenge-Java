package br.com.alura.ChallengeForumHub.domain.dto.view;

import br.com.alura.ChallengeForumHub.domain.StatusTopico;
import br.com.alura.ChallengeForumHub.domain.Topico;

import java.time.LocalDateTime;

public record TopicoView (Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        String curso,
        Long autorId){

    public TopicoView(Topico topico){
        this(topico.getId(),
             topico.getTitulo(),
             topico.getMensagem(),
             topico.getDataCriacao(),
             topico.getStatus(),
             topico.getCurso(),
             topico.getAutorId());
    }
}
