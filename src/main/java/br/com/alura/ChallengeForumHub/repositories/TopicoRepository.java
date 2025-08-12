package br.com.alura.ChallengeForumHub.repositories;

import br.com.alura.ChallengeForumHub.domain.StatusTopico;
import br.com.alura.ChallengeForumHub.domain.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TopicoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TopicoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Topico criarTopico(Topico topico){
        String sql = "INSERT INTO topico(titulo, mensagem, satus, autor_id, curso) VALUES (?, ?, ?, ?, ?) RETURNING *;";
        return jdbcTemplate.queryForObject(sql,
                (resultSet, i) -> getTopico(resultSet),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getStatus().name(),
                topico.getAutorId(),
                topico.getCurso());
    }

    public Topico buscarTopicoPorId(Long id){
        String sql = "SELECT * FROM topico WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> getTopico(resultSet), id);
    }

    public Page<Topico> lintarTodosTopicos(String nomeCurso, Integer ano, Pageable paginacao){
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM topico WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if(nomeCurso != null){
            sqlBuilder.append(" AND curso = ? ");
            params.add(nomeCurso);
        }

        if(ano != null){
            sqlBuilder.append(" AND EXTRACT(YEAR FROM data_criacao) = ?");
            params.add(ano);
        }

        String countSql = "SELECT count(1) FROM (" + sqlBuilder +") as count_query";
        long total = jdbcTemplate.queryForObject(countSql, Long.class, params.toArray());

        sqlBuilder.append(" ORDER BY ").append(paginacao.getSort().toString().replace(":", ""));
        sqlBuilder.append(" LIMIT ? OFFSET ?");
        params.add(paginacao.getPageSize());
        params.add(paginacao.getOffset());

        List<Topico> topicos = jdbcTemplate.query(sqlBuilder.toString(), (resultSet, i) -> getTopico(resultSet), params.toArray());

        return new PageImpl<>(topicos, paginacao, total);
    }

    private Topico getTopico(ResultSet resultSet) throws SQLException {
        return Topico.builder()
                .id(resultSet.getLong("id"))
                .titulo(resultSet.getString("titulo"))
                .mensagem(resultSet.getString("mensagem"))
                .dataCriacao(resultSet.getTimestamp("data_criacao").toLocalDateTime())
                .status(StatusTopico.valueOf(resultSet.getString("status")))
                .curso(resultSet.getString("curso"))
                .autorId(resultSet.getLong("autor_id"))
                .build();
    }

}
