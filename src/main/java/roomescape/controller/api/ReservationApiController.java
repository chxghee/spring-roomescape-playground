package roomescape.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponseList = reservations.stream()
                .map(ReservationResponse::new)
                .toList();
        return ResponseEntity.ok(reservationResponseList);
    }
}
