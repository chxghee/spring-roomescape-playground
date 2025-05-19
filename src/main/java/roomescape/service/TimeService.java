package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeDAO;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.entity.Time;
import roomescape.exception.NotFoundException;

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

    public void deleteTimeTable(Long timeId) {
        if (timeDAO.findById(timeId).isEmpty()) {
            throw new NotFoundException("시간 정보 없음", "삭제 요청한 " + timeId + "번 타임은 존재하지 않습니다!");
        }
        timeDAO.delete(timeId);
    }

}
