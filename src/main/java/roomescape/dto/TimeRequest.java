package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.entity.Time;
import roomescape.exception.EmptyValueException;
import roomescape.utils.DateTimeParser;

public class TimeRequest {

    private final String time;

    @JsonCreator
    public TimeRequest(@JsonProperty("time") String time) {
        this.time = time;
    }


    public Time toEntity() {
        return new Time(DateTimeParser.parseTime(time));
    }

    public void validateRequiredField() {
        if (time == null || time.isBlank()) {
            throw new EmptyValueException("필수 입력값 누락", "time 값은 필수입니다!");
        }
    }

    public String getTime() {
        return time;
    }
}
