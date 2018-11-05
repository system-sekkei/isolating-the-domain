package example.application.service;

import example.Application;
import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.attendance.TimeRecord;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.DayOfMonth;
import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class AttendanceServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    AttendanceService sut;

    @Test
    void register() {
        UserIdentifier userId = userService.list().list().get(0).identifier();
        DayOfMonth workDay = new DayOfMonth("2099-10-20");
        TimeRecord workTime = new TimeRecord(new HourTime("9:00"), new HourTime("17:00"), new Minute(60));
        sut.registerWorkTime(userId, workDay, workTime);
        TimeRecord registeredWorkTime = sut.findBy(userId, workDay);
        assertAll(() -> assertEquals(workTime.start().value(), registeredWorkTime.start().value()),
                () -> assertEquals(workTime.end().value(), registeredWorkTime.end().value()),
                () -> assertEquals(workTime.breaks().value(), registeredWorkTime.breaks().value()));
    }
}
