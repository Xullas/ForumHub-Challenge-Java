package br.com.alura.ChallengeForumHub.domain.form;

import br.com.alura.ChallengeForumHub.domain.StatusTopico;
import br.com.alura.ChallengeForumHub.domain.validation.NaAtualizacao;
import br.com.alura.ChallengeForumHub.domain.validation.NaCriacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoForm(

        @NotBlank(groups = NaCriacao.class)
        String titulo,
        @NotBlank(groups = NaCriacao.class)
        String mensagem,
        @NotNull(groups = NaCriacao.class)
        StatusTopico statusTopico,
        @NotBlank(groups = NaCriacao.class)
        String curso,
        @NotNull(groups = {NaCriacao.class, NaAtualizacao.class})
        Long autorId) {
}
