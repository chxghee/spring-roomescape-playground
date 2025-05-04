package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.utils.DateTimeParser;


public class ReservationRequest {

    private final String date;
    private final String name;
    private final String time;

    public ReservationRequest(String date, String name, String time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, DateTimeParser.parseDate(date), DateTimeParser.parseTime(time));
    }

    public boolean isDateBlankOrNull() {
        return date == null || date.isBlank();
    }

    public boolean isNameBlankOrNull() {
        return name == null || name.isBlank();
    }

    public boolean isTimeBlankOrNull() {
        return name == null || name.isBlank();
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
