package roomescape.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.exception.EmptyValueException;
import roomescape.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class TimeServiceTest {

    @Autowired
    private TimeService timeService;

    @Test
    void 시간_추가시_입력_시간이_공백이면_예외가_발생해야_한다() {
        TimeRequest timeRequest = new TimeRequest("");

        assertThatThrownBy(() -> timeService.createTimeTable(timeRequest))
                .isInstanceOf(EmptyValueException.class);
    }

    @Test
    void 등록된_시간이_없다면_빈_리스트가_반환되어야_한다() {
        List<TimeResponse> allTimes = timeService.getAllTimeTable();
        assertThat(allTimes.size()).isEqualTo(0);
    }

    @Test
    void 삭제_요청한_시간이_존재하지_않으면_예외가_발생해야한다() {
        Long timeId = 1L;
        assertThatThrownBy(() -> timeService.deleteTimeTable(timeId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void 시간을_추가하면_조회시_포함되어야_한다() {
        TimeRequest timeRequest = new TimeRequest("12:00");
        timeService.createTimeTable(timeRequest);

        List<TimeResponse> allTimes = timeService.getAllTimeTable();

        assertThat(allTimes).hasSize(1);
        assertThat(allTimes.get(0).getTime()).isEqualTo("12:00");
    }

    @Test
    void 시간을_삭제하면_조회시_포함되지_말아야_한다() {
        TimeRequest timeRequest = new TimeRequest("12:00");
        TimeResponse timeTable = timeService.createTimeTable(timeRequest);

        Long timeId = timeTable.getId();
        timeService.deleteTimeTable(timeId);

        List<TimeResponse> allTimes = timeService.getAllTimeTable();
        assertThat(allTimes).hasSize(0);
        assertThat(allTimes).noneMatch(t -> t.getId().equals(timeId));
    }
}
