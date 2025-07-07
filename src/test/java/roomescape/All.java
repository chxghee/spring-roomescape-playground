package roomescape;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import roomescape.dao.ReservationDAOTest;
import roomescape.dao.TimeDAOTest;
import roomescape.service.ReservationService;
import roomescape.service.TimeServiceTest;

@Suite
@SelectClasses({
        TimeDAOTest.class,
        TimeServiceTest.class,
        ReservationDAOTest.class,
        ReservationService.class,
        MissionStepTest.class
})
public class All {
}
