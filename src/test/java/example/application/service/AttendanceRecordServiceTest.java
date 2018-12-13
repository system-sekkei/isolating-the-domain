package example.application.service;

import example.Application;
import example.application.service.attendance.AttendanceQueryService;
import example.application.service.attendance.AttendanceRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.attendance.*;
import example.domain.model.worker.ContractingWorkers;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 勤怠情報のテスト
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class AttendanceRecordServiceTest {

    @Autowired
    WorkerQueryService workerQueryService;
    @Autowired
    AttendanceRecordService attendanceRecordService;
    @Autowired
    AttendanceQueryService attendanceQueryService;

    @Test
    @Transactional
    void 登録した勤怠情報を取得できる() {
        ContractingWorkers workers = workerQueryService.contractingWorkers();
        Worker worker = workers.list().get(0);
        WorkerNumber workerNumber = worker.workerNumber();

        int year = 2099;
        int month = 10;
        int day = 20;
        WorkDay workDay = new WorkDay(LocalDate.of(year, month, day));

        Attendance expectAttendance = new Attendance(workDay, new WorkStartTime(new ClockTime("9:00")), new WorkEndTime(new ClockTime("17:00")), new NormalBreakTime(new Minute(60)), new MidnightBreakTime("0"));
        attendanceRecordService.registerAttendance(workerNumber, expectAttendance);

        MonthlyAttendances monthlyAttendances = attendanceQueryService.findMonthlyAttendances(workerNumber, new WorkMonth(year, month));
        assertAll(
                () -> assertEquals(monthlyAttendances.month().toStringWithUnit(), month + "月"),
                () -> assertEquals(monthlyAttendances.attendances().list().size(), 1)
        );

        Date startDate = new Date(LocalDate.of(year, month, day));
        Date endDate = new Date(LocalDate.of(year, month, day));
        Attendances attendances = attendanceQueryService.getAttendances(workerNumber, startDate, endDate);
        WorkTime workTime = attendances.summarize();
        assertAll(
                () -> assertEquals(attendances.list().size(), 1),
                () -> assertEquals(workTime.normalTime().toString(), "07:00")
        );

        Attendance actualAttendance = attendances.list().get(0);
        assertAll(() -> assertTrue(expectAttendance.workDay().hasSameValue(actualAttendance.workDay())),
                () -> assertEquals(expectAttendance.workTimeRange().start().toString(), actualAttendance.workTimeRange().start().toString()),
                () -> assertEquals(expectAttendance.workTimeRange().end().toString(), actualAttendance.workTimeRange().end().toString()),
                () -> assertEquals(expectAttendance.totalBreakTime().toString(), actualAttendance.totalBreakTime().toString()));
    }

}
