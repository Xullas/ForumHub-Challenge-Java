package br.com.alura.ChallengeForumHub.controller;

import br.com.alura.ChallengeForumHub.domain.form.UsuarioForm;
import br.com.alura.ChallengeForumHub.domain.view.UsuarioView;
import br.com.alura.ChallengeForumHub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional //TODO Verificar onde fica a anotação e o path do URI
    public ResponseEntity criarUsuario(@RequestBody @Valid UsuarioForm usuarioForm, UriComponentsBuilder uriBuilder) {
        UsuarioView usuarioView = usuarioService.criarUsuario(usuarioForm);
        URI uri = uriBuilder.path("usuario").buildAndExpand(usuarioView.id()).toUri();
        return ResponseEntity.created(uri).body(usuarioView);
    }
}
