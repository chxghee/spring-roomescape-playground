package roomescape.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.EmptyValueException;
import roomescape.exception.NotFoundReservationException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    void 예약_추가시_공백인_항목이_있으면_예외가_발생해야_한다() {
        ReservationRequest reservationRequest = new ReservationRequest("2025-05-08", "", "12:00");
        assertThatThrownBy(() -> reservationService.createReservation(reservationRequest))
                .isInstanceOf(EmptyValueException.class);
    }

    @Test
    void 예약_추가에_성공하면_예약번호가_생성되어야_한다() {
        ReservationRequest reservationRequest = new ReservationRequest("2025-05-08", "브라운", "12:00");
        ReservationResponse reservation = reservationService.createReservation(reservationRequest);
        assertThat(reservation.getId()).isInstanceOf(Long.class);
    }

    @Test
    void 등록된_예약이_없다면_빈_리스트가_반환되어야_한다() {
        List<ReservationResponse> allReservations = reservationService.getAllReservations();
        assertThat(allReservations.size()).isEqualTo(0);
    }

    @Test
    void 삭제_요청한_예약번호가_존재하지_않으면_예외가_발생해야한다() {
        Long reservationId = 1L;
        assertThatThrownBy(() -> reservationService.deleteReservation(reservationId))
                .isInstanceOf(NotFoundReservationException.class);
    }

    @Test
    void 예약을_추가하면_조회시_포함되어야_한다() {
        ReservationRequest request = new ReservationRequest("2023-08-05", "브라운", "15:40");
        reservationService.createReservation(request);

        List<ReservationResponse> allReservations = reservationService.getAllReservations();
        assertThat(allReservations).hasSize(1);
        assertThat(allReservations.get(0).getName()).isEqualTo("브라운");
    }

    @Test
    void 예약을_삭제하면_조회시_포함되지_말아야_한다() {
        ReservationRequest request = new ReservationRequest("2023-08-05", "브라운", "15:40");
        ReservationResponse reservation = reservationService.createReservation(request);
        Long reservationId = reservation.getId();

        reservationService.deleteReservation(reservationId);

        List<ReservationResponse> allReservations = reservationService.getAllReservations();
        assertThat(allReservations).hasSize(0);
        assertThat(allReservations).noneMatch(r -> r.getId().equals(reservationId));
    }
}
