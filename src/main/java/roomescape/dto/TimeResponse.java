package roomescape.dto;

import roomescape.entity.Time;

import java.time.format.DateTimeFormatter;

public class TimeResponse {

    private final Long id;
    private final String time;

    private TimeResponse(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static TimeResponse of(Time time) {
        return new TimeResponse(
                time.getId(),
                time.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }

    public static TimeResponse withId(Long id, Time time) {
        return new TimeResponse(
                id,
                time.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
