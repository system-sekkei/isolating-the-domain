package example.domain.model.timerecord;

import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.breaktime.NightBreakTime;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ActualWorkTimeTest {

    @DisplayName("就業時間の計算を正しく行えること")
    @ParameterizedTest
    @CsvSource({
            "9:00, 18:00, 60, 8時間0分",
            // 休憩時間
            "9:00, 18:00, 61, 7時間45分",
            "9:00, 18:00, 75, 7時間45分",
            "9:00, 18:00, 76, 7時間30分",
            // 始業時間
            "9:01, 18:00, 60, 7時間45分",
            "9:15, 18:00, 60, 7時間45分",
            "9:16, 18:00, 60, 7時間30分",
            // 終業時間
            "9:00, 18:01, 60, 8時間0分",
            "9:00, 18:14, 60, 8時間0分",
            "9:00, 18:15, 60, 8時間15分",
            // 組み合わせ
            "9:16, 18:00, 76, 7時間0分",
            "9:01, 18:01, 59, 7時間45分",
    })
    void workTime(String begin, String end, int breaks, String expected) {
        ActualWorkTime sut = new ActualWorkTime(
                new TimeRange(new StartTime(begin), new EndTime(end)),
                new DaytimeBreakTime(new Minute(breaks)),
                new NightBreakTime(new Minute("0")));
        assertEquals(expected, sut.daytimeWorkTime().toString());
    }

    @DisplayName("深夜時間帯の作業時間を正しく返却できること")
    @ParameterizedTest
    @CsvSource({
            "18:00, 27:00, 60, 4時間0分",
            "8:00, 17:00, 0, 0時間0分"
    })
    void nightWorkTime(String begin, String end, int breaks, String expected) {
        ActualWorkTime sut = new ActualWorkTime(
                new TimeRange(new StartTime(begin), new EndTime(end)),
                new DaytimeBreakTime(new Minute(0)),
                new NightBreakTime(new Minute(breaks)));
        assertEquals(expected, sut.nightWorkTime().toString());
    }

    @DisplayName("時間外作業時間を正しく返却できること")
    @ParameterizedTest
    @CsvSource({
            "9:00, 17:00, 60, 0時間0分",
            "09:00, 22:00, 60, 4時間0分"})
    void overWorkTime(String begin, String end, int breaks, String expected) {
        ActualWorkTime sut = new ActualWorkTime(
                new TimeRange(new StartTime(begin), new EndTime(end)),
                new DaytimeBreakTime(new Minute(breaks)), new NightBreakTime(new Minute("0")));
        assertEquals(expected, sut.overWorkTime().toString());
    }

    @DisplayName("就業時間/時間外就業時間/深夜作業時間/休憩時間の相関")
    @Test
    void 時間の仕様() {
        ActualWorkTime sut = new ActualWorkTime(
                new TimeRange(new StartTime("8:00"), new EndTime("24:00")),
                new DaytimeBreakTime(new Minute(120)),
                new NightBreakTime(new Minute("30")));
        assertAll(
                () -> assertEquals("12時間0分", sut.daytimeWorkTime().toString())
                , () -> assertEquals("5時間30分", sut.overWorkTime().toString())
                , () -> assertEquals("1時間30分", sut.nightWorkTime().toString())
        );
    }
}