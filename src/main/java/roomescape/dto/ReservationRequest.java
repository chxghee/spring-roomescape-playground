package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.entity.Time;
import roomescape.exception.EmptyValueException;
import roomescape.utils.DateTimeParser;

import java.util.ArrayList;
import java.util.List;


public class ReservationRequest {

    private final String date;
    private final String name;
    private final String time;

    public ReservationRequest(String date, String name, String time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public Reservation toEntity(Time time) {
        return new Reservation(name, DateTimeParser.parseDate(date), time);
    }

    public void validateRequiredFields() {
        List<String> nullOrBlankFiled = getNullOrBlankFiled();
        if (!nullOrBlankFiled.isEmpty()) {
            throw new EmptyValueException("필수 입력값 누락", nullOrBlankFiled.toString() + " 값이 비어 있습니다!");
        }
    }

    private  List<String> getNullOrBlankFiled() {
        List<String> results = new ArrayList<>();
        if (date == null || date.isBlank()) {
            results.add("date");
        }
        if (name == null || name.isBlank()) {
            results.add("name");
        }
        if (time == null || time.isBlank()) {
            results.add("time");
        }
        return results;
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
