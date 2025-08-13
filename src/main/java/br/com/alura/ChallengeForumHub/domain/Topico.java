package br.com.alura.ChallengeForumHub.domain;

import br.com.alura.ChallengeForumHub.domain.dto.form.TopicoForm;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@With
public class Topico {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private StatusTopico status;
    private String curso;
    private Long autorId;

    public Topico(TopicoForm topicoForm) {
        this.titulo = topicoForm.titulo();
        this.mensagem = topicoForm.mensagem();
        this.status = topicoForm.statusTopico();
        this.curso = topicoForm.curso();
        this.autorId = topicoForm.autorId();
    }
}
