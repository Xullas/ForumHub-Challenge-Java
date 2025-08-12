package br.com.alura.ChallengeForumHub.service;

import br.com.alura.ChallengeForumHub.domain.Usuario;
import br.com.alura.ChallengeForumHub.domain.form.UsuarioForm;
import br.com.alura.ChallengeForumHub.domain.view.UsuarioView;
import br.com.alura.ChallengeForumHub.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioView criarUsuario(UsuarioForm usuarioForm) {
        Usuario usuario = new Usuario(usuarioForm);
        long usuarioID = usuarioRepository.criarUsuario(usuario);
        UsuarioView usuarioView = new UsuarioView(usuario.withId(usuarioID));
        return usuarioView;
    }
}
