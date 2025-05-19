package roomescape.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeApiController {

    private final TimeService timeService;

    public TimeApiController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTimeTable(@RequestBody TimeRequest timeRequest) {
        TimeResponse timeTable = timeService.createTimeTable(timeRequest);
        return ResponseEntity.created(URI.create("/times"))
                .body(timeTable);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getAllTimeTable() {
        return ResponseEntity.ok(timeService.getAllTimeTable());
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity<Void> deleteTimeTable(@PathVariable("timeId") Long timeId) {
        timeService.deleteTimeTable(timeId);
        return ResponseEntity.noContent().build();
    }
}
