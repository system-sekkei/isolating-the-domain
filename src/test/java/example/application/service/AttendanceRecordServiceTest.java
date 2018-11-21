package example.application.service;

import example.Application;
import example.application.service.attendance.AttendanceQueryService;
import example.application.service.attendance.AttendanceRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.attendance.*;
import example.domain.model.worker.WorkerNumber;
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
    WorkerQueryService workerQueryService;
    @Autowired
    AttendanceRecordService sut;
    @Autowired
    AttendanceQueryService attendanceQueryService;

    @Test
    void register() {
        WorkerNumber workerNumber = workerQueryService.contractingWorkers().list().get(0).workerNumber();
        Date workDay = new Date("2099-10-20");
        AttendanceOfDay work = new AttendanceOfDay(workDay, new WorkStartTime(new HourTime("9:00")), new WorkEndTime(new HourTime("17:00")), new Break(new Minute(60)));

        sut.registerAttendance(workerNumber, work);

        MonthlyAttendances monthlyAttendances = attendanceQueryService.findMonthlyAttendances(workerNumber, workDay.yearMonth());
        AttendanceOfDay registeredAttendance = monthlyAttendances.attendanceOf(workDay);
        assertAll(() -> assertEquals(work.date().value(), registeredAttendance.date().value()),
                () -> assertEquals(work.workTimeRange().start().toString(), registeredAttendance.workTimeRange().start().toString()),
                () -> assertEquals(work.workTimeRange().end().toString(), registeredAttendance.workTimeRange().end().toString()),
                () -> assertEquals(work.breaks().toString(), registeredAttendance.breaks().toString()));
    }
}
