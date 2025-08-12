package br.com.alura.ChallengeForumHub.controller;

import br.com.alura.ChallengeForumHub.domain.form.TopicoForm;
import br.com.alura.ChallengeForumHub.domain.view.TopicoView;
import br.com.alura.ChallengeForumHub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("topico")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity criarTopico(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
        TopicoView topicoView = topicoService.criarTopico(topicoForm);
        URI uri = uriBuilder.path("usuario/{id}").buildAndExpand(topicoView.id()).toUri();
        return ResponseEntity.created(uri).body(topicoView);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTopico(@PathVariable Long id){
        return ResponseEntity.ok(topicoService.buscarTopicoPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<TopicoView>> listarTodosTopicos(
            @RequestParam(required = false) String nomeCurso,
            @RequestParam(required = false) Integer ano,
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable paginacao) {

        Page<TopicoView> paginaDeTopicos = topicoService.listarTodosTopicos(nomeCurso, ano, paginacao);
        return ResponseEntity.ok(paginaDeTopicos);
    }
}
