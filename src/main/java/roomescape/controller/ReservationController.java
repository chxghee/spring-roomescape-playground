package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponseList = reservations.stream()
                .map(ReservationResponse::new)
                .toList();
        return ResponseEntity.ok(reservationResponseList);
    }
}
