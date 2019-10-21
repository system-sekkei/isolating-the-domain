package example.application.service;

import example.Application;
import example.application.service.attendance.AttendanceQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordRecordService;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.*;
import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.breaktime.NightBreakTime;
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
class TimeRecordRecordServiceTest {

    @Autowired
    EmployeeQueryService employeeQueryService;
    @Autowired
    TimeRecordRecordService timeRecordRecordService;
    @Autowired
    AttendanceQueryService attendanceQueryService;

    @Test
    @Transactional
    void 登録した勤怠情報を取得できる() {
        ContractingEmployees employees = employeeQueryService.contractingEmployees();
        Employee employee = employees.list().get(0);
        EmployeeNumber employeeNumber = employee.employeeNumber();

        int year = 2017;
        int month = 10;
        int day = 20;
        WorkDate workDate = new WorkDate(new Date(LocalDate.of(year, month, day)));
        ActualWorkDateTime actualWorkDateTime = new ActualWorkDateTime(
                new WorkRange(
                        new StartDateTime(workDate, new StartTime(new ClockTime("9:00"))),
                        new EndDateTime(workDate, 24, 0)),
                new DaytimeBreakTime(new Minute(60)),
                new NightBreakTime(new Minute(0)));

        TimeRecord expectTimeRecord = new TimeRecord(employeeNumber, actualWorkDateTime);
        timeRecordRecordService.registerTimeRecord(expectTimeRecord);

        Attendance attendance = attendanceQueryService.findAttendance(employee, new WorkMonth(year, month));
        assertAll(
                () -> assertEquals(attendance.month().toStringWithUnit(), year + "年" + month + "月"),
                () -> assertEquals(attendance.listWorkDates().size(), 31),
                () -> assertTrue(attendance.statusOf(workDate).isWork())
        );
    }
}
