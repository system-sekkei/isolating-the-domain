package example.domain.type.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClockTimeRangeTest {

    @DisplayName("時間間隔取得テスト")
    @ParameterizedTest
    @CsvSource({"9:00, 17:00, 08:00", "9:00, 9:30, 00:30", "9:30, 10:00, 00:30", "20:00, 2:00, 06:00"})
    void differenceTime(String fromTime, String toTime, String rangeTime) {
        ClockTime from = new ClockTime(fromTime);
        ClockTime to = new ClockTime(toTime);

        ClockTimeRange range = new ClockTimeRange(from, to);
        HourAndMinute result = range.between();

        assertEquals(rangeTime, result.toString());
    }

    @DisplayName("積集合のテスト")
    @ParameterizedTest
    @CsvSource({
            //業務的な範囲
            "9:00, 17:00, 8:00, 16:00, 09:00, 16:00",
            "19:00, 3:00, 22:00, 5:00, 22:00, 03:00",
            "19:00, 23:00, 22:00, 5:00, 22:00, 23:00",
            "01:00, 03:00, 22:00, 5:00, 01:00, 03:00",
            //数学的な範囲
            "8:00, 11:00, 7:00, 8:00, 00:00, 00:00",
            "8:00, 11:00, 7:00, 9:00, 08:00, 09:00",
            "8:00, 11:00, 9:00, 10:00, 09:00, 10:00",
            "8:00, 11:00, 10:00, 12:00, 10:00, 11:00",
            "8:00, 11:00, 11:00, 12:00, 00:00, 00:00",
            "8:00, 11:00, 7:00, 12:00, 08:00, 11:00"
    })
    void intercect(String start1, String end1, String start2, String end2, String expectedStart, String expectedEnd) {
        ClockTimeRange r1 = new ClockTimeRange(new ClockTime(start1), new ClockTime(end1));
        ClockTimeRange r2 = new ClockTimeRange(new ClockTime(start2), new ClockTime(end2));
        ClockTimeRange sut = r1.intersect(r2);
        assertAll(() -> assertEquals(expectedStart, sut.begin.toString()),
                () -> assertEquals(expectedEnd, sut.end.toString()));
    }
}