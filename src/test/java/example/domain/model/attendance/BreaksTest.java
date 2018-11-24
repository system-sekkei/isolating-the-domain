package example.domain.model.attendance;

import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BreaksTest {

    @DisplayName("休憩時間は15分刻みで切り上げて計算される")
    @ParameterizedTest
    @CsvSource({
            "0, 60",
            "1, 45",
            "14, 45",
            "15, 45",
            "16, 30"
    })
    void normalizeMinute(int breakMinute, String expectedWorkMinute) {
        NormalBreakTime breakTime = new NormalBreakTime(new Minute(breakMinute));

        Minute workMinute = new Minute(60);
        assertEquals(expectedWorkMinute, breakTime.subtractFrom(workMinute).toString());
    }
}
