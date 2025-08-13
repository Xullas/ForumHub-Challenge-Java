package br.com.alura.ChallengeForumHub.service;

import br.com.alura.ChallengeForumHub.domain.Usuario;
import br.com.alura.ChallengeForumHub.domain.dto.form.UsuarioForm;
import br.com.alura.ChallengeForumHub.domain.dto.view.UsuarioView;
import br.com.alura.ChallengeForumHub.exception.TratadorDeErros;
import br.com.alura.ChallengeForumHub.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioView criarUsuario(UsuarioForm usuarioForm) {
        Usuario usuario = new Usuario(usuarioForm);
        long usuarioID = usuarioRepository.criarUsuario(usuario);
        UsuarioView usuarioView = new UsuarioView(usuario.withId(usuarioID));
        return usuarioView;
    }

    public UsuarioView buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new TratadorDeErros.ResourceNotFoundException(MessageFormat.format("Usuário com o id {0} não encontrado.", id));
        }
        UsuarioView usuarioView = new UsuarioView(usuario);
        return usuarioView;
    }
}
