package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final String time;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public ReservationResponse(Long id, Reservation reservation) {
        this.id = id;
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
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
