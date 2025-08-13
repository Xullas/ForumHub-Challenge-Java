package br.com.alura.ChallengeForumHub.domain.dto.form;

import br.com.alura.ChallengeForumHub.domain.StatusTopico;
import br.com.alura.ChallengeForumHub.domain.validation.NaAtualizacao;
import br.com.alura.ChallengeForumHub.domain.validation.NaCriacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoForm(

        @NotBlank(message = "O título não pode estar em branco.", groups = NaCriacao.class)
        String titulo,
        @NotBlank(message = "A mensagem não pode estar em branco.", groups = NaCriacao.class)
        String mensagem,
        @NotNull(message = "O status do tópico é obrigatório.", groups = NaCriacao.class)
        StatusTopico statusTopico,
        @NotBlank(message = "O nome do curso não pode estar em branco.", groups = NaCriacao.class)
        String curso,
        @NotNull(message = "O ID do autor é obrigatório.", groups = {NaCriacao.class, NaAtualizacao.class})
        Long autorId) {
}
