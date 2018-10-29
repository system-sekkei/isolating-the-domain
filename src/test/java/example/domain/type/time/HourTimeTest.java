package example.domain.type.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class HourTimeTest {
    @Test
    @DisplayName("文字列変換が正しくできること")
    void translate() {
        final String text = "12:32";
        HourTime ht = new HourTime(text);
        assertEquals(text, ht.toString());
    }
}
