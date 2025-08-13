package br.com.alura.ChallengeForumHub.domain.dto.form;

import jakarta.validation.constraints.NotBlank;

public record UsuarioForm(
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String senha) {
}
