package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.entity.Time;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TimeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final TimeRowMapper timeRowMapper;

    public TimeDAO(JdbcTemplate jdbcTemplate, TimeRowMapper timeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
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
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Optional<Time> findById(Long id) {
        final String sql = "select * from time where id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, timeRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Time> findByTime(LocalTime time) {
        final String sql = "select * from time where time = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, timeRowMapper, time));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void delete(Long timeId) {
        final String sql = "delete from time where id = ?";
        jdbcTemplate.update(sql, timeId);
    }

}
