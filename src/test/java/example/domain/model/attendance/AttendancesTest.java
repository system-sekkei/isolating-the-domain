package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.date.DateRange;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AttendancesTest {

    @Test
    void test() {
        List<Attendance> list = Arrays.asList(
                new Attendance(new WorkDay("2018-11-01")),
                new Attendance(new WorkDay("2018-11-11")),
                new Attendance(new WorkDay("2018-11-23")),
                new Attendance(new WorkDay("2018-11-25")),
                new Attendance(new WorkDay("2018-12-01"))
        );
        Attendances sut = new Attendances(list);

        Attendances attendances = sut.rangeOf(new DateRange(new Date("2018-11-02"), new Date("2018-11-24")));

        assertEquals(2, attendances.list().size());
        assertTrue(attendances.list().stream().anyMatch(attendance -> attendance.workDay().toString().equals("2018-11-11")));
        assertTrue(attendances.list().stream().anyMatch(attendance -> attendance.workDay().toString().equals("2018-11-23")));
    }
}