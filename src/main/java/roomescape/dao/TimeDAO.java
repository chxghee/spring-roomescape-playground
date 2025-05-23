package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.entity.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TimeDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final TimeRowMapper timeRowMapper;

    public TimeDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate, TimeRowMapper timeRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
        this.timeRowMapper = timeRowMapper;
    }

    public Long insert(Time time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", java.sql.Time.valueOf(time.getTime()));

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public List<Time> findAll() {
        final String sql = "select * from time";
        return namedParameterJdbcTemplate.query(sql, timeRowMapper);
    }

    public Optional<Time> findById(Long id) {
        final String sql = "select * from time where id = :id";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource("id", id);

        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, namedParameter, timeRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void delete(Long timeId) {
        final String sql = "delete from time where id = :id";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource("id", timeId);
        namedParameterJdbcTemplate.update(sql, namedParameter);
    }
}
