package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final ReservationRowMapper reservationRowMapper;

    public ReservationDAO(JdbcTemplate jdbcTemplate, ReservationRowMapper reservationRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationRowMapper = reservationRowMapper;
    }

    public Long insert(Reservation reservation) {
        final var query = "insert into reservation(name, date, time) values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, java.sql.Date.valueOf(reservation.getDate()));
            ps.setTime(3, java.sql.Time.valueOf(reservation.getTime()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Reservation> findAll() {
        final var query = "select * from reservation";

        return jdbcTemplate.query(query, reservationRowMapper);
    }

    public Optional<Reservation> findById(Long id) {
        final var query = "select * from reservation where id = ?";

        try {
            Reservation reservation = jdbcTemplate.queryForObject(query, reservationRowMapper, id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void update(Long reservationId, ReservationRequest reservationRequest) {
        final var query = "update reservation set name = ?, date = ?, time = ? where id = ?";
        jdbcTemplate.update(query,
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTime(),
                reservationId);
    }

    public void delete(Long reservationId) {
        final var query = "delete from reservation where id = ?";
        jdbcTemplate.update(query, reservationId);
    }
}
