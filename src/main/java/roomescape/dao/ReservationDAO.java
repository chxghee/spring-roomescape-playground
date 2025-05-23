package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ReservationDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final ReservationRowMapper reservationRowMapper;
    private final String FIND_QUERY = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                                    "from reservation as r " +
                                    "inner join time as t on r.time_id = t.id ";

    public ReservationDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate, ReservationRowMapper reservationRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        this.reservationRowMapper = reservationRowMapper;
    }

    public Long insert(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", Date.valueOf(reservation.getDate()));
        parameters.put("time_id", reservation.getTime().getId());

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public List<Reservation> findAll() {
        return namedParameterJdbcTemplate.query(FIND_QUERY, reservationRowMapper);
    }

    public Optional<Reservation> findById(Long id) {
        final String query = FIND_QUERY + " where r.id = :id";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource("id", id);

        try {
            Reservation reservation = namedParameterJdbcTemplate.queryForObject(query, namedParameter, reservationRowMapper);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void update(Long reservationId, ReservationRequest reservationRequest) {
        final String query = "update reservation set name = :name, date = :date where id = :id";

        MapSqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("name", reservationRequest.getName())
                .addValue("date", reservationRequest.getDate())
                .addValue("id", reservationId);

        namedParameterJdbcTemplate.update(query,
                namedParameter);
    }

    public void delete(Long reservationId) {
        final String query = "delete from reservation where id = :id";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource("id", reservationId);
        namedParameterJdbcTemplate.update(query, namedParameter);
    }
}
