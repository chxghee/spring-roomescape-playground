package roomescape.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import roomescape.entity.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class TimeDAOTest {

    @Autowired
    private TimeDAO timeDAO;

    @Test
    void insertTime() {
        Time time = new Time(LocalTime.now());

        timeDAO.insert(time);
        List<Time> findTimes = timeDAO.findAll();

        assertThat(findTimes.size()).isEqualTo(1);
    }

    @Test
    void findTimes() {
        timeDAO.insert(new Time(LocalTime.parse("10:00")));
        timeDAO.insert(new Time(LocalTime.parse("12:00")));

        List<Time> findTimes = timeDAO.findAll();

        assertSoftly(soft -> {
            soft.assertThat(findTimes).hasSize(2);
            soft.assertThat(findTimes.get(0).getTime()).isEqualTo("10:00");
            soft.assertThat(findTimes.get(1).getTime()).isEqualTo("12:00");
        });
    }

    @Test
    void findReservationById() {
        Long timeId = timeDAO.insert(new Time(LocalTime.parse("10:00")));

        Optional<Time> findTime = timeDAO.findById(timeId);

        assertSoftly(soft -> {
            soft.assertThat(findTime).isPresent();
            soft.assertThat(findTime.get().getTime()).isEqualTo("10:00");
        });
    }


    @Test
    void deleteReservation() {
        Long timeId = timeDAO.insert(new Time(LocalTime.parse("10:00")));

        timeDAO.delete(timeId);
        Optional<Time> findTime = timeDAO.findById(timeId);

        assertThat(findTime).isEmpty();
    }
}
