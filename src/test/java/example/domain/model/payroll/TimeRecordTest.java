package example.domain.model.payroll;

import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeRecordTest {
    @DisplayName("HourTimeを15分刻みに変換出来ること(切り捨て)")
    @ParameterizedTest
    @CsvSource({"10:14, 10:00", "10:15, 10:15", "10:16, 10:15"})
    void normalizeHourTime(String org, String normalize) {
        HourTime ht = new HourTime(org);
        assertEquals(TimeRecord.normalize(ht).toString(), normalize);
    }

    @DisplayName("Minuteを15分刻みに変換出来ること(切り上げ")
    @ParameterizedTest
    @CsvSource({"0, 0", "1, 15", "14, 15", "15, 15", "16, 30"})
    void normalizeMinute(int org, int normalize) {
        Minute m = new Minute(org);
        assertEquals(TimeRecord.normalize(m).value(), normalize);
    }
}