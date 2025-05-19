package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final ReservationRowMapper reservationRowMapper;
    private final String FIND_QUERY = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                                    "from reservation as r " +
                                    "inner join time as t on r.time_id = t.id ";

    public ReservationDAO(JdbcTemplate jdbcTemplate, ReservationRowMapper reservationRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
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
        return jdbcTemplate.query(FIND_QUERY, reservationRowMapper);
    }

    public Optional<Reservation> findById(Long id) {
        final String query = FIND_QUERY + " where r.id = ?";

        try {
            Reservation reservation = jdbcTemplate.queryForObject(query, reservationRowMapper, id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void update(Long reservationId, ReservationRequest reservationRequest) {
        final String query = "update reservation set name = ?, date = ? where id = ?";
        jdbcTemplate.update(query,
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationId);
    }

    public void delete(Long reservationId) {
        final String query = "delete from reservation where id = ?";
        jdbcTemplate.update(query, reservationId);
    }
}
