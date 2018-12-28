package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.ClockTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkStartTimeTest {
    @DisplayName("勤務時刻は15分刻みで切り捨てられる")
    @ParameterizedTest
    @CsvSource({"10:14, 10:00", "10:15, 10:15", "10:16, 10:15"})
    void normalizeHourTime(String org, String normalize) {
        WorkStartTime ht = new WorkStartTime(new ClockTime(org));
        assertEquals(normalize, ht.normalizedHourTime().toString());
    }
}
