package example.application.service;

import example.application.coordinator.timerecord.TimeRecordCoordinator;
import example.domain.model.timerecord.evaluation.TimeRecordValidError;
import example.application.service.attendance.AttendanceQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordRecordService;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.ActualWorkDateTime;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.timerecord.evaluation.WorkDate;
import example.presentation.controller.timerecord.AttendanceForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 勤怠情報のテスト
 */
@SpringBootTest
@Transactional
class TimeRecordRecordServiceTest {

    @Autowired
    EmployeeQueryService employeeQueryService;
    @Autowired
    TimeRecordRecordService timeRecordRecordService;
    @Autowired
    AttendanceQueryService attendanceQueryService;
    @Autowired
    TimeRecordCoordinator timeRecordCoordinator;

    @Test
    void 登録した勤怠情報を取得できる() {
        EmployeeNumber employeeNumber = new EmployeeNumber(1);
        Employee employee = employeeQueryService.choose(employeeNumber);

        TimeRecord registerTimeRecord = new TimeRecord(employeeNumber, AttendanceForm.toActualWorkDateTime("2000-10-20", "9:00", "25:00", "60", "30"));
        timeRecordRecordService.registerTimeRecord(registerTimeRecord);

        Attendance attendance = attendanceQueryService.findAttendance(employee, new WorkMonth("2000-10"));
        TimeRecord timeRecord = attendance.at(new WorkDate("2000-10-20"));
        ActualWorkDateTime actualWorkDateTime = timeRecord.actualWorkDateTime();

        assertEquals("14時間30分", actualWorkDateTime.workTime().toString());
    }

    @Test
    void 同じ日の勤怠情報を登録すると更新される() {
        登録した勤怠情報を取得できる();

        EmployeeNumber employeeNumber = new EmployeeNumber(1);
        Employee employee = employeeQueryService.choose(employeeNumber);

        timeRecordRecordService.registerTimeRecord(
                new TimeRecord(employeeNumber, AttendanceForm.toActualWorkDateTime("2000-10-20", "8:30", "25:00", "60", "30")));
        timeRecordRecordService.registerTimeRecord(
                new TimeRecord(employeeNumber, AttendanceForm.toActualWorkDateTime("2000-10-20", "10:30", "25:00", "60", "30")));

        Attendance attendance = attendanceQueryService.findAttendance(employee, new WorkMonth("2000-10"));
        TimeRecord timeRecord = attendance.at(new WorkDate("2000-10-20"));
        ActualWorkDateTime actualWorkDateTime = timeRecord.actualWorkDateTime();

        assertEquals("13時間0分", actualWorkDateTime.workTime().toString());
    }

    @Test
    void 前の勤務日と勤務時刻が重複するかチェックできる() {
        EmployeeNumber employeeNumber = new EmployeeNumber(1);

        timeRecordRecordService.registerTimeRecord(
                new TimeRecord(employeeNumber, AttendanceForm.toActualWorkDateTime("2000-10-19", "8:30", "33:00", "60", "30")));

        TimeRecord timeRecord = new TimeRecord(employeeNumber, AttendanceForm.toActualWorkDateTime("2000-10-20", "8:59", "25:00", "60", "30"));

        assertEquals(TimeRecordValidError.前日の勤務時刻と重複, timeRecordCoordinator.isValid(timeRecord).errors().get(0));
    }

    @Test
    void 次の勤務日と勤務時刻が重複するかチェックできる() {
        EmployeeNumber employeeNumber = new EmployeeNumber(1);

        timeRecordRecordService.registerTimeRecord(
                new TimeRecord(employeeNumber, AttendanceForm.toActualWorkDateTime("2000-10-21", "8:30", "18:00", "60", "30")));

        TimeRecord timeRecord = new TimeRecord(employeeNumber, AttendanceForm.toActualWorkDateTime("2000-10-20", "8:00", "33:00", "60", "30"));

        assertEquals(TimeRecordValidError.翌日の勤務時刻と重複, timeRecordCoordinator.isValid(timeRecord).errors().get(0));
    }
}
