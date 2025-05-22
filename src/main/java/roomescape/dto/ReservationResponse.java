package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.entity.Time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final TimeResponse time;

    public ReservationResponse(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = TimeResponse.of(time);
    }

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public static ReservationResponse withId(Long id, Reservation reservation) {
        return new ReservationResponse(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public TimeResponse getTime() {
        return time;
    }
}
