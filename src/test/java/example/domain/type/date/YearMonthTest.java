package example.domain.type.date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class YearMonthTest {
    @DisplayName("文字列コンストラクタからオブジェクトが作成できること")
    @Test
    void String_to_YearMonth() {
        YearMonth sut = new YearMonth("2018-10");
        assertEquals(2018, sut.year().value().intValue());
        assertEquals(10, sut.month().value().intValue());
    }

    @DisplayName("月末月初の取得処理が正しい事")
    @ParameterizedTest
    @CsvSource({"2018, 2, 1, 28", "2020, 2, 1, 29"})
    void start_and_end(int year, int month, int start, int end) {
        YearMonth ym = new YearMonth(year, month);
        assertAll(() -> assertEquals(start, ym.start().value().getDayOfMonth()),
                () -> assertEquals(end, ym.end().value().getDayOfMonth()));
    }

    @DisplayName("月次Streamが正しく動いていること")
    @ParameterizedTest
    @CsvSource({"2018, 1, 31", "2018, 2, 28", "2018, 4, 30"})
    void days(int year, int month, int times) {
        YearMonth ym = new YearMonth(year, month);
        assertEquals(times, (int)ym.days().count());
    }
}
