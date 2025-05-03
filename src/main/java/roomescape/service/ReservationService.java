package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;

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
        Reservation newReservation = reservationRequest.toEntity(index.incrementAndGet());
        reservations.add(newReservation);
        return new ReservationResponse(newReservation);
    }

    public void deleteReservation(Long reservationId) {
        reservations.removeIf(reservation -> reservation.getId().equals(reservationId));
    }

}
