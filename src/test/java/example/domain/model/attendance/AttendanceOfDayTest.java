package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AttendanceOfDayTest {
    @DisplayName("勤務時刻は15分刻みで切り捨てられる")
    @ParameterizedTest
    @CsvSource({"10:14, 10:00", "10:15, 10:15", "10:16, 10:15"})
    void normalizeHourTime(String org, String normalize) {
        WorkStartTime ht = new WorkStartTime(new HourTime(org));
        assertEquals(normalize, ht.normalizedHourTime().toString());
    }

    @DisplayName("休憩時間を15分刻みで切り上げられる")
    @ParameterizedTest
    @CsvSource({"0, 0", "1, 15", "14, 15", "15, 15", "16, 30"})
    void normalizeMinute(int org, String normalize) {
        Break m = new Break(new Minute(org));
        assertEquals(normalize, m.normalizeValue().toString());
    }

    @DisplayName("就業時間の計算を正しく行えること")
    @ParameterizedTest
    @CsvSource({"9:00, 18:00, 60, 08:00", "9:01, 18:14, 46, 08:00"})
    void workTime(String begin, String end, int breaks, String expected) {
        AttendanceOfDay sut = new AttendanceOfDay(Date.now(), new HourTime(begin), new HourTime(end), new Minute(breaks));
        assertEquals(expected, sut.workTime().toString());
    }
}