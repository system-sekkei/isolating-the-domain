package example.domain.model.workrecord;

import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkTimeRecordTest {

    @DisplayName("就業時間の計算を正しく行えること")
    @ParameterizedTest
    @CsvSource({
            "9:00, 18:00, 60, 08:00",
            // 休憩時間
            "9:00, 18:00, 61, 07:45",
            "9:00, 18:00, 75, 07:45",
            "9:00, 18:00, 76, 07:30",
            // 始業時間
            "9:01, 18:00, 60, 07:45",
            "9:15, 18:00, 60, 07:45",
            "9:16, 18:00, 60, 07:30",
            // 終業時間
            "9:00, 18:01, 60, 08:00",
            "9:00, 18:14, 60, 08:00",
            "9:00, 18:15, 60, 08:15",
            // 組み合わせ
            "9:16, 18:00, 76, 07:00",
            "9:01, 18:01, 59, 07:45",
    })
    void workTime(String begin, String end, int breaks, String expected) {
        WorkTimeRecord sut = new WorkTimeRecord(
                new WorkTimeRange(new WorkStartTime(begin), new WorkEndTime(end)),
                new DaytimeBreakTime(new Minute(breaks)),
                new MidnightBreakTime(new Minute("0")));
        assertEquals(expected, sut.daytimeWorkTime().toString());
    }

    @DisplayName("深夜時間帯の作業時間を正しく返却できること")
    @ParameterizedTest
    @CsvSource({
            "18:00, 27:00, 60, 04:00",
            "8:00, 17:00, 0, 00:00"
    })
    void midnightWorkTime(String begin, String end, int breaks, String expected) {
        WorkTimeRecord sut = new WorkTimeRecord(
                new WorkTimeRange(new WorkStartTime(begin), new WorkEndTime(end)),
                new DaytimeBreakTime(new Minute(0)),
                new MidnightBreakTime(new Minute(breaks)));
        assertEquals(expected, sut.midnightWorkTime().toString());
    }

    @DisplayName("時間外作業時間を正しく返却できること")
    @ParameterizedTest
    @CsvSource({
            "9:00, 17:00, 60, 00:00",
            "09:00, 22:00, 60, 04:00"})
    void overWorkTime(String begin, String end, int breaks, String expected) {
        WorkTimeRecord sut = new WorkTimeRecord(
                new WorkTimeRange(new WorkStartTime(begin), new WorkEndTime(end)),
                new DaytimeBreakTime(new Minute(breaks)), new MidnightBreakTime(new Minute("0")));
        assertEquals(expected, sut.overWorkTime().toString());
    }

    @DisplayName("就業時間/時間外就業時間/深夜作業時間/休憩時間の相関")
    @Test
    void 時間の仕様() {
        WorkTimeRecord sut = new WorkTimeRecord(
                new WorkTimeRange(new WorkStartTime("8:00"), new WorkEndTime("24:00")),
                new DaytimeBreakTime(new Minute(120)),
                new MidnightBreakTime(new Minute("30")));
        assertAll(
                () -> assertEquals("12:00", sut.daytimeWorkTime().toString(), "就業時間は就業時間から休憩時間を引いた値です。")
                , () -> assertEquals("05:30", sut.overWorkTime().toString(), "時間外作業時間は就業時間から8時間を減算した値です。")
                , () -> assertEquals("01:30", sut.midnightWorkTime().toString(), "深夜作業時間は深夜時間帯の作業時間から深夜休憩時間を引いた値です。")
        );
    }
}