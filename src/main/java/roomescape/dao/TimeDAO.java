package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.entity.Time;

import java.util.HashMap;
import java.util.Map;

@Component
public class TimeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public Long insert(Time time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", java.sql.Time.valueOf(time.getTime()));

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }


}
