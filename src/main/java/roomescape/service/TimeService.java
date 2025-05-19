package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeDAO;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.entity.Time;

import java.util.List;

@Service
public class TimeService {

    private final TimeDAO timeDAO;

    public TimeService(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public TimeResponse createTimeTable(TimeRequest timeRequest) {
        timeRequest.validateRequiredField();
        Time newTime = timeRequest.toEntity();
        Long newTimeId = timeDAO.insert(newTime);
        return TimeResponse.withId(newTimeId, newTime);
    }

    public List<TimeResponse> getAllTimeTable() {
        return timeDAO.findAll().stream()
                .map(TimeResponse::of)
                .toList();
    }


}
