package br.com.alura.ChallengeForumHub.repositories;

import br.com.alura.ChallengeForumHub.domain.StatusTopico;
import br.com.alura.ChallengeForumHub.domain.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
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
        String sql = "INSERT INTO topico(titulo, mensagem, status, autor_id, curso) VALUES (?, ?, ?, ?, ?) RETURNING *;";
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


    public int atualizarTopico(Topico topico, Long id){
        StringBuilder sqlBuilder = new StringBuilder("UPDATE topico SET ");
        List<Object> params = new ArrayList<>();
        boolean isFirst = true;

        Field[] fields = topico.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object fieldValue = field.get(topico);
                if(fieldValue != null && !field.getName().equals("id") && !field.getName().equals("dataCriacao") && !field.getName().equals("autorId")){
                    if (!isFirst) {
                        sqlBuilder.append(", ");
                    }
                    sqlBuilder.append(field.getName()).append(" = ? ");
                    if(fieldValue instanceof Enum){
                        params.add(((Enum<?>) fieldValue).name());
                    } else {
                        params.add(fieldValue);
                    }
                    isFirst = false;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        sqlBuilder.append(" WHERE id = ?;");
        params.add(id);

        String sqlFinal = sqlBuilder.toString();

        return jdbcTemplate.update(sqlFinal, params.toArray());
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

    public int deletarTopico(Long id) {
        String sql = "DELETE FROM topico WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
