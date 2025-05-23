package roomescape;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import roomescape.service.TimeServiceTest;

@Suite
@SelectClasses({
        TimeServiceTest.class,
        MissionStepTest.class
})
public class All {
}
