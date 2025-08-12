package br.com.alura.ChallengeForumHub.domain;

import br.com.alura.ChallengeForumHub.domain.form.UsuarioForm;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@With
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(UsuarioForm usuarioForm) {
        this.nome = usuarioForm.nome();
        this.email = usuarioForm.email();
        this.senha = usuarioForm.senha();
    }
}
