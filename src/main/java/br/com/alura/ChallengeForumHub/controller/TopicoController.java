package br.com.alura.ChallengeForumHub.controller;

import br.com.alura.ChallengeForumHub.domain.form.TopicoForm;
import br.com.alura.ChallengeForumHub.domain.validation.NaAtualizacao;
import br.com.alura.ChallengeForumHub.domain.validation.NaCriacao;
import br.com.alura.ChallengeForumHub.domain.view.TopicoView;
import br.com.alura.ChallengeForumHub.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity criarTopico(@RequestBody @Validated(NaCriacao.class) TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
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
            @PageableDefault(size = 10, sort = "data_criacao", direction = Sort.Direction.ASC) Pageable paginacao) {

        Page<TopicoView> paginaDeTopicos = topicoService.listarTodosTopicos(nomeCurso, ano, paginacao);
        return ResponseEntity.ok(paginaDeTopicos);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarTopico(@PathVariable long id, @RequestBody @Validated(NaAtualizacao.class) TopicoForm topicoForm){
        TopicoView topicoView = topicoService.atualizarTopico(id, topicoForm);
        return ResponseEntity.ok(topicoView);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarTopico(@PathVariable Long id){
        topicoService.deletarTopico(id);
        return ResponseEntity.ok("Topico com ID: " + id + " excluído!");
    }
}
