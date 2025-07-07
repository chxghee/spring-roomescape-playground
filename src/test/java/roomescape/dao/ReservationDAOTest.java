package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.entity.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class ReservationDAOTest {

    @Autowired
    private ReservationDAO reservationDAO;
    @Autowired
    private TimeDAO timeDAO;
    private Time time;

    @BeforeEach
    void setUp() {
        LocalTime sampleTime = LocalTime.parse("12:00");
        Long timeId = timeDAO.insert(new Time(sampleTime));
        time = new Time(timeId, sampleTime);
    }

    @Test
    void insertReservation() {
        Reservation newReservation = new Reservation("이창희", LocalDate.now(), time);

        reservationDAO.insert(newReservation);
        List<Reservation> reservations = reservationDAO.findAll();

        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    void findReservation() {
        reservationDAO.insert(new Reservation("이창희", LocalDate.now(), time));
        reservationDAO.insert(new Reservation("전서희", LocalDate.now(), time));

        List<Reservation> findReservations = reservationDAO.findAll();

        assertSoftly(soft -> {
            soft.assertThat(findReservations).hasSize(2);
            soft.assertThat(findReservations.get(0).getName()).isEqualTo("이창희");
            soft.assertThat(findReservations.get(1).getName()).isEqualTo("전서희");
        });
    }

    @Test
    void findReservationById() {
        Long id = reservationDAO.insert(new Reservation("이창희", LocalDate.parse("2025-05-05"), time));

        Optional<Reservation> findReservation = reservationDAO.findById(id);

        assertSoftly(soft -> {
            soft.assertThat(findReservation).isPresent();
            soft.assertThat(findReservation.get().getName()).isEqualTo("이창희");
            soft.assertThat(findReservation.get().getDate()).isEqualTo("2025-05-05");
            soft.assertThat(findReservation.get().getTime().getTime()).isEqualTo("12:00");
        });
    }

    @Test
    void updateReservation() {
        Long id = reservationDAO.insert(new Reservation("이창희", LocalDate.now(), time));
        ReservationRequest updateRequest = new ReservationRequest("2025-05-05", "전서희", time.getId());

        reservationDAO.update(id, updateRequest);
        Optional<Reservation> updatedReservation = reservationDAO.findById(id);

        assertSoftly(soft -> {
            soft.assertThat(updatedReservation).isPresent();
            soft.assertThat(updatedReservation.get().getName()).isEqualTo(updateRequest.getName());
            soft.assertThat(updatedReservation.get().getDate()).isEqualTo(updateRequest.getDate());
            soft.assertThat(updatedReservation.get().getTime().getId()).isEqualTo(updateRequest.getTime());
        });
    }

    @Test
    void deleteReservation() {
        Long id = reservationDAO.insert(new Reservation("이창희", LocalDate.now(), time));

        reservationDAO.delete(id);
        Optional<Reservation> findReservation = reservationDAO.findById(id);

        assertThat(findReservation).isEmpty();
    }
}
