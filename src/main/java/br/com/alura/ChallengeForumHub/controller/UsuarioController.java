package br.com.alura.ChallengeForumHub.controller;

import br.com.alura.ChallengeForumHub.domain.dto.form.UsuarioForm;
import br.com.alura.ChallengeForumHub.domain.dto.view.UsuarioView;
import br.com.alura.ChallengeForumHub.domain.validation.NaCriacao;
import br.com.alura.ChallengeForumHub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity criarUsuario(@RequestBody @Validated(NaCriacao.class) UsuarioForm usuarioForm, UriComponentsBuilder uriBuilder) {
        UsuarioView usuarioView = usuarioService.criarUsuario(usuarioForm);
        URI uri = uriBuilder.path("usuario/{id}").buildAndExpand(usuarioView.id()).toUri();
        return ResponseEntity.created(uri).body(usuarioView);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUsuario(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

}
