package example.domain.model.payroll;

import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeRecordTest {
    @DisplayName("HourTimeを15分刻みに変換出来ること(切り捨て)")
    @ParameterizedTest
    @CsvSource({"10:14, 10:00", "10:15, 10:15", "10:16, 10:15"})
    void normalizeHourTime(String org, String normalize) {
        HourTime ht = new HourTime(org);
        assertEquals(normalize, TimeRecord.normalize(ht).toString());
    }

    @DisplayName("Minuteを15分刻みに変換出来ること(切り上げ")
    @ParameterizedTest
    @CsvSource({"0, 0", "1, 15", "14, 15", "15, 15", "16, 30"})
    void normalizeMinute(int org, int normalize) {
        Minute m = new Minute(org);
        assertEquals(normalize, TimeRecord.normalize(m).value());
    }

    @DisplayName("就業時間の計算を正しく行えること")
    @ParameterizedTest
    @CsvSource({"9:00, 18:00, 60, 08:00", "9:01, 18:14, 46, 08:00"})
    void workTime(String begin, String end, int breaks, String expected) {
        TimeRecord sut = new TimeRecord(new HourTime(begin), new HourTime(end), new Minute(breaks));
        assertEquals(expected, sut.workTime().toString());
    }
}