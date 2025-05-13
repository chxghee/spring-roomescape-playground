package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.exception.EmptyValueException;
import roomescape.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationDAO.findAll().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        reservationRequest.validateRequiredFields();
        Reservation newReservation = reservationRequest.toEntity();
        Long id = reservationDAO.insert(newReservation);
        return new ReservationResponse(id, newReservation);
    }

    public void deleteReservation(Long reservationId) {

        if (reservationDAO.findById(reservationId).isEmpty()) {
            throw new NotFoundReservationException("예약 정보 없음", "삭제 요청한 " + reservationId + "번 예약은 존재하지 않습니다!");
        }
        reservationDAO.delete(reservationId);
    }

}
