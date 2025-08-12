package br.com.alura.ChallengeForumHub.domain.form;

import br.com.alura.ChallengeForumHub.domain.StatusTopico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoForm(

        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotNull
        StatusTopico statusTopico,
        @NotBlank
        String curso,
        @NotNull
        Long autorId) {
}
