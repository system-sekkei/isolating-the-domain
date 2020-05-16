package example.domain.model.timerecord;

import example.domain.model.timerecord.evaluation.ActualWorkDateTime;
import example.presentation.controller.timerecord.AttendanceForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ActualWorkTimeTest {

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
            "0:00, 24:00, 60, 16時間0分",
            "9:00, 33:00, 60, 16時間0分",
    })
    void 日中作業時間が計算できる(String begin, String end, String breaks, String expected) {
        ActualWorkDateTime sut = AttendanceForm.toActualWorkDateTime("2018-11-25", begin, end, breaks, "0");
        assertEquals(expected, sut.daytimeWorkTime().toString());
    }

    @ParameterizedTest
    @CsvSource({
            "1:00, 2:00, 0, 1時間0分",
            "22:00, 23:00, 0, 1時間0分",
            "18:00, 27:00, 60, 4時間0分",
            "8:00, 17:00, 0, 0時間0分",
            "0:00, 24:00, 0, 7時間0分",
            "23:00, 24:00, 0, 1時間0分",
    })
    void 深夜作業時間が計算できる(String begin, String end, String breaks, String expected) {
        ActualWorkDateTime sut = AttendanceForm.toActualWorkDateTime("2018-11-25", begin, end, "0", breaks);
        assertEquals(expected, sut.nightWorkTime().toString());
    }

    @ParameterizedTest
    @CsvSource({
            "9:00, 17:00, 60, 0時間0分",
            "09:00, 22:00, 60, 4時間0分"})
    void 法定時間外労働月60時間以内時間外作業時間が計算できる(String begin, String end, String breaks, String expected) {
        ActualWorkDateTime sut = AttendanceForm.toActualWorkDateTime("2018-11-25", begin, end, breaks, "0");
        assertEquals(expected, sut.overLegalWithin60HoursWorkTime().toString());
    }

    @Test
    void 作業時間が計算できる() {
        ActualWorkDateTime sut = AttendanceForm.toActualWorkDateTime("2018-11-25", "8:00", "24:00", "120", "30");
        assertAll(
                () -> assertEquals("12時間0分", sut.daytimeWorkTime().toString())
                , () -> assertEquals("5時間30分", sut.overLegalWithin60HoursWorkTime().toString())
                , () -> assertEquals("1時間30分", sut.nightWorkTime().toString())
        );
    }
}