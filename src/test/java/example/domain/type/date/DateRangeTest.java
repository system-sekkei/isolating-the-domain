package example.domain.type.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DateRangeTest {
    @DisplayName("日付範囲のイテレータが正しく動作する事")
    @Test
    void days() {
        DateRange sut = new DateRange(
                new Date(LocalDate.of(2018, 12, 30)),
                new Date(LocalDate.of(2019, 1, 2)));
        List<Date> days = sut.days();
        assertAll(
                () -> assertEquals(4, days.size()),
                () -> assertEquals(LocalDate.of(2018, 12, 30), days.get(0).value()),
                () -> assertEquals(LocalDate.of(2018, 12, 31), days.get(1).value()),
                () -> assertEquals(LocalDate.of(2019, 1, 1), days.get(2).value()),
                () -> assertEquals(LocalDate.of(2019, 1, 2), days.get(3).value())
                );
    }
}
