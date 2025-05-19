package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final String time;

    public ReservationResponse(Long id, String name, LocalDate date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }

    public static ReservationResponse withId(Long id, Reservation reservation) {
        return new ReservationResponse(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
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

    public String getTime() {
        return time;
    }
}
