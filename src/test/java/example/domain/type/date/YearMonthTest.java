package example.domain.type.date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YearMonthTest {
    @DisplayName("文字列コンストラクタからオブジェクトが作成できること")
    @Test
    void String_to_YearMonth() {
        YearMonth sut = new YearMonth("2018-10");
        assertEquals(2018, sut.year().value().intValue());
        assertEquals(10, sut.month().value().intValue());
    }
}
