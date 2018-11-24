package example.domain.model.attendance;

import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BreaksTest {
    @DisplayName("休憩時間を15分刻みで切り上げられる")
    @ParameterizedTest
    @CsvSource({"0, 0", "1, 15", "14, 15", "15, 15", "16, 30"})
    void normalizeMinute(int org, String normalize) {
        NormalBreakTime m = new NormalBreakTime(new Minute(org));
        assertEquals(normalize, m.normalizeValue().toString());
    }
}
