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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class AttendanceQueryServiceTest {

    @Autowired
    AttendanceQueryService queryService;

    @Autowired
    WorkerQueryService workerQueryService;

    @Autowired
    AttendanceRecordService recordService;

    @Test
    void getAttendances() {

        Date startDate = new Date(LocalDate.of(2099, 10, 1));
        Date endDate = new Date(LocalDate.of(2099, 10, 31));

        ContractingWorkers workers = workerQueryService.contractingWorkers();
        Worker worker = workers.list().get(0);
        WorkerNumber workerNumber = worker.workerNumber();
        Attendances attendances = queryService.getAttendances(workerNumber, startDate, endDate);
        WorkTime workTime = attendances.summarize();

        assertAll(
                () -> assertEquals(attendances.list().size(), 31),
                () -> assertEquals(workTime.normalTime().toString(), "00:00")
        );
    }

    @Test
    void findMonthlyAttendances() {
        int year = 2099;
        int month = 10;
        int day = 20;

        ContractingWorkers workers = workerQueryService.contractingWorkers();
        Worker worker = workers.list().get(0);
        WorkerNumber workerNumber = worker.workerNumber();

        WorkDay workDay = new WorkDay(LocalDate.of(year, month, day));
        Attendance work = new Attendance(workDay, new WorkStartTime(new ClockTime("9:00")), new WorkEndTime(new ClockTime("17:00")), new NormalBreakTime(new Minute(60)), new MidnightBreakTime("0"));
        recordService.registerAttendance(workerNumber, work);

        WorkMonth workMonth = new WorkMonth(year, month);

        MonthlyAttendances monthlyAttendances = queryService.findMonthlyAttendances(workerNumber, workMonth);

        assertAll(
                () -> assertEquals(monthlyAttendances.month().toStringWithUnit(), month + "月"),
                () -> assertEquals(monthlyAttendances.attendances().list().size(), 1)
        );
    }


}
