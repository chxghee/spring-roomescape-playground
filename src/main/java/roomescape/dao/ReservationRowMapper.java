package roomescape.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.entity.Reservation;
import roomescape.entity.Time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

@Component
public class ReservationRowMapper implements RowMapper<Reservation> {

    @Override
    public Reservation mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Long timeId = resultSet.getLong("time_id");
        LocalTime timeValue = resultSet.getTime("time_value").toLocalTime();
        Time time = new Time(timeId, timeValue);

        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                time
        );
    }
}
