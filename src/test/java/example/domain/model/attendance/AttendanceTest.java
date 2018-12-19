package example.domain.model.attendance;

import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AttendanceTest {
    @DisplayName("就業時間の計算を正しく行えること")
    @ParameterizedTest
    @CsvSource({"9:00, 18:00, 60, 08:00", "9:01, 18:14, 46, 08:00"})
    void workTime(String begin, String end, int breaks, String expected) {
        Attendance sut = new Attendance(new WorkDay(), new WorkStartTime(new ClockTime(begin)), new WorkEndTime(new ClockTime(end)), new NormalBreakTime(new Minute(breaks)), new MidnightBreakTime(new Minute("0")));
        assertEquals(expected, sut.workTime().toString());
    }

    @DisplayName("深夜時間帯の作業時間を正しく返却できること")
    @ParameterizedTest
    @CsvSource({
            "18:00, 3:00, 60, 04:00",
            "8:00, 17:00, 0, 00:00"
    })
    void midnightWorkTime(String begin, String end, int breaks, String expected) {
        Attendance sut = new Attendance(
                new WorkDay(),
                new WorkStartTime(new ClockTime(begin)), new WorkEndTime(new ClockTime(end)),
                new NormalBreakTime(new Minute(0)),
                new MidnightBreakTime(new Minute(breaks))
        );
        assertEquals(expected, sut.midnightWorkTime().toString());
    }

    @DisplayName("時間外作業時間を正しく返却できること")
    @ParameterizedTest
    @CsvSource({"9:00, 17:00, 60, 00:00", "09:00, 22:00, 60, 04:00"})
    void overWorkTime(String begin, String end, int breaks, String expected) {
        Attendance sut = new Attendance(new WorkDay(), new WorkStartTime(new ClockTime(begin)), new WorkEndTime(new ClockTime(end)), new NormalBreakTime(new Minute(breaks)), new MidnightBreakTime(new Minute("0")));
        assertEquals(expected, sut.overTime().toString());
    }

    @DisplayName("就業時間/時間外就業時間/深夜作業時間/休憩時間の相関")
    @Test
    void 時間の仕様() {
        Attendance sut = new Attendance(new WorkDay(),
                new WorkStartTime(new ClockTime("8:00")), new WorkEndTime(new ClockTime("00:00")),
                new NormalBreakTime(new Minute(120)), new MidnightBreakTime(new Minute("30")));
        assertAll(
                () -> assertEquals("12:00", sut.workTime().toString(), "就業時間は就業時間から休憩時間を引いた値です。")
                , () -> assertEquals("05:30", sut.overTime().toString(), "時間外作業時間は就業時間から8時間を減算した値です。")
                , () -> assertEquals("01:30", sut.midnightWorkTime().toString(), "深夜作業時間は深夜時間帯の作業時間から深夜休憩時間を引いた値です。")
        );
    }
}