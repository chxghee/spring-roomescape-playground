package roomescape.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.*;

@SpringBootTest
class ReservationDAOTest {

    @Autowired
    private ReservationDAO reservationDAO;

    @BeforeEach
    void 테스트DB_초기화() {
        try (var connection = reservationDAO.getConnection();
             var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM RESERVATION");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addReservation() {
        Reservation reservation = new Reservation(1L, "전서희", LocalDate.parse("2025-05-12"), LocalTime.parse("19:00"));
        reservationDAO.addReservation(reservation);
    }

    @Test
    void findReservation() {
        Reservation reservation = new Reservation(1L, "전서희", LocalDate.parse("2025-05-12"), LocalTime.parse("19:00"));
        reservationDAO.addReservation(reservation);

        Optional<Reservation> findReservation = reservationDAO.findReservation(reservation.getId());

        assertSoftly(soft -> {
            soft.assertThat(findReservation.isPresent()).isTrue();
            soft.assertThat(findReservation.get().getId()).isEqualTo(reservation.getId());
            soft.assertThat(findReservation.get().getName()).isEqualTo(reservation.getName());
            soft.assertThat(findReservation.get().getDate()).isEqualTo(reservation.getDate());
            soft.assertThat(findReservation.get().getTime()).isEqualTo(reservation.getTime());
        });
    }

    @Test
    void updateReservation() {
        Reservation reservation = new Reservation(1L, "전서희", LocalDate.parse("2025-05-12"), LocalTime.parse("19:00"));
        reservationDAO.addReservation(reservation);
        ReservationRequest updateRequest = new ReservationRequest( "2025-05-13","이창희", "20:00");

        reservationDAO.updateReservation(1L, updateRequest);
        Reservation findReservation = reservationDAO.findReservation(reservation.getId()).get();

        assertSoftly(soft -> {
            soft.assertThat(findReservation.getName()).isEqualTo(updateRequest.getName());
            soft.assertThat(findReservation.getDate()).isEqualTo(updateRequest.getDate());
            soft.assertThat(findReservation.getTime()).isEqualTo(updateRequest.getTime());
        });
    }

    @Test
    void deleteReservation() {
        Reservation reservation = new Reservation(1L, "전서희", LocalDate.parse("2025-05-12"), LocalTime.parse("19:00"));
        reservationDAO.addReservation(reservation);
        Long deleteId = 1L;

        reservationDAO.deleteReservation(deleteId);
        Optional<Reservation> findReservation = reservationDAO.findReservation(deleteId);

        assertThat(findReservation.isEmpty()).isTrue();
    }

}
