package br.com.alura.ChallengeForumHub.domain;

import br.com.alura.ChallengeForumHub.domain.dto.form.UsuarioForm;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@With
public class Usuario  implements UserDetails {
    private Long id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(UsuarioForm usuarioForm) {
        this.nome = usuarioForm.nome();
        this.email = usuarioForm.email();
        this.senha = usuarioForm.senha();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
