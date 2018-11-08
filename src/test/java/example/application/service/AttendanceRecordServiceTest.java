package example.application.service;

import example.Application;
import example.application.service.attendance.AttendanceQueryService;
import example.application.service.attendance.AttendanceRecordService;
import example.application.service.worker.WorkerService;
import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.worker.WorkerIdentifier;
import example.domain.type.date.Date;
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
class AttendanceRecordServiceTest {
    @Autowired
    WorkerService workerService;
    @Autowired
    AttendanceRecordService sut;
    @Autowired
    AttendanceQueryService attendanceQueryService;

    @Test
    void register() {
        WorkerIdentifier userId = workerService.list().list().get(0).identifier();
        Date workDay = new Date("2099-10-20");
        AttendanceOfDay work = new AttendanceOfDay(workDay, new HourTime("9:00"), new HourTime("17:00"), new Minute(60));

        sut.registerWorkTime(userId, work);

        AttendanceOfDay registeredAttendance = attendanceQueryService.findBy(userId, workDay);
        assertAll(() -> assertEquals(work.date().value(), registeredAttendance.date().value()),
                () -> assertEquals(work.workTimeRange().start().toString(), registeredAttendance.workTimeRange().start().toString()),
                () -> assertEquals(work.workTimeRange().end().toString(), registeredAttendance.workTimeRange().end().toString()),
                () -> assertEquals(work.breaks().toString(), registeredAttendance.breaks().toString()));
    }
}
