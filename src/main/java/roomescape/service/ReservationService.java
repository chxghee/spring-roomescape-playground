package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDAO;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import roomescape.exception.NotFoundException;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final TimeDAO timeDAO;

    public ReservationService(ReservationDAO reservationDAO, TimeDAO timeDAO) {
        this.reservationDAO = reservationDAO;
        this.timeDAO = timeDAO;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationDAO.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        reservationRequest.validateRequiredFields();
        Time time = getReservationTimeAt(reservationRequest.getTime());
        Reservation newReservation = reservationRequest.toEntity(time);
        Long id = reservationDAO.insert(newReservation);
        return ReservationResponse.withId(id, newReservation);
    }

    private Time getReservationTimeAt(Long reservationTime) {
        return timeDAO.findById(reservationTime)
                .orElseThrow(() -> new NotFoundException("시간 정보 없음", "예약을 요청한 " + reservationTime + "번 타임은 존재하지 않습니다!"));
    }

    public void deleteReservation(Long reservationId) {

        if (reservationDAO.findById(reservationId).isEmpty()) {
            throw new NotFoundException("예약 정보 없음", "삭제 요청한 " + reservationId + "번 예약은 존재하지 않습니다!");
        }
        reservationDAO.delete(reservationId);
    }

}
