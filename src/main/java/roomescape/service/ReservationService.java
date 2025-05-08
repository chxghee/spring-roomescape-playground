package roomescape.service;

import org.springframework.stereotype.Service;
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

    private AtomicLong index = new AtomicLong(0);
    private List<Reservation> reservations = new ArrayList<>();

    public List<ReservationResponse> getAllReservations() {
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        validateRequiredFields(reservationRequest);
        Reservation newReservation = reservationRequest.toEntity(index.incrementAndGet());
        reservations.add(newReservation);
        return new ReservationResponse(newReservation);
    }

    public void deleteReservation(Long reservationId) {
        boolean isRemoved = reservations.removeIf(reservation -> reservation.getId().equals(reservationId));
        if (!isRemoved) {
            throw new NotFoundReservationException("예약 정보 없음", "삭제 요청한 " + reservationId + "번 예약은 존재하지 않습니다!");
        }
    }

    private void validateRequiredFields(ReservationRequest reservationRequest) {
        List<String> nullOrBlankFiled = getNullOrBlankFiled(reservationRequest);
        if (!nullOrBlankFiled.isEmpty()) {
            throw new EmptyValueException("필수 입력값 누락", nullOrBlankFiled.toString() + " 값이 비어 있습니다!");
        }
    }

    private List<String> getNullOrBlankFiled(ReservationRequest reservationRequest) {
        ArrayList<String> results = new ArrayList<>();
        if (reservationRequest.isDateBlankOrNull()) {
            results.add("date");
        }
        if (reservationRequest.isNameBlankOrNull()) {
            results.add("name");
        }
        if (reservationRequest.isTimeBlankOrNull()) {
            results.add("time");
        }
        return results;
    }
}
