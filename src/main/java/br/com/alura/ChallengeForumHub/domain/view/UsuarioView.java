package br.com.alura.ChallengeForumHub.domain.view;

import br.com.alura.ChallengeForumHub.domain.Usuario;

public record UsuarioView(
        Long id,
        String nome,
        String email) {

    public UsuarioView(Usuario usuario) {
        this(usuario.getId(),
             usuario.getNome(),
             usuario.getEmail());
    }
}
