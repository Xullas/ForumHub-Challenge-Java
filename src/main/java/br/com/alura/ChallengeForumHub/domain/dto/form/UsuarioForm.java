package br.com.alura.ChallengeForumHub.domain.dto.form;

import br.com.alura.ChallengeForumHub.domain.validation.NaCriacao;
import jakarta.validation.constraints.NotBlank;

public record UsuarioForm(
        @NotBlank(message = "O nome não pode estar em branco.", groups = NaCriacao.class)
        String nome,
        @NotBlank(message = "O email não pode estar em branco.")
        String email,
        @NotBlank(message = "A senha não pode estar em branco.")
        String senha) {
}
