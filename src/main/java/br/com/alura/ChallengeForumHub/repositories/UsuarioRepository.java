package br.com.alura.ChallengeForumHub.repositories;

import br.com.alura.ChallengeForumHub.domain.Usuario;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Long criarUsuario(Usuario usuario){
        String sql = "INSERT INTO usuario(nome, email, senha) VALUES (?, ?, ?) RETURNING id;";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, usuario.getNome());
                    ps.setString(2, usuario.getEmail());
                    ps.setString(3, usuario.getSenha());
                    return ps;
                },
                keyHolder
        );

        Long generatedID = keyHolder.getKey().longValue();

        return generatedID;
    }

    public Usuario buscarUsuarioPorId(Long id){
        try {
            String sql = "SELECT * FROM usuario WHERE id = ?;";
            return jdbcTemplate.queryForObject(sql, (resultSet, i) -> getUsuario(resultSet), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public UserDetails buscarUsuarioPorEmail(String login) {
        String sql = "SELECT * FROM usuario WHERE email = ?;";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> getUsuario(resultSet), login);
    }

    private Usuario getUsuario(ResultSet resultSet) throws SQLException {
        return Usuario.builder()
                .id(resultSet.getLong("id"))
                .nome(resultSet.getString("nome"))
                .email(resultSet.getString("email"))
                .senha(resultSet.getString("senha"))
                .build();
    }

}
