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
import example.domain.model.timerecord.evaluation.ActualWorkDateTime;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.timerecord.evaluation.WorkDate;
import example.presentation.controller.timerecord.AttendanceForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

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

        ActualWorkDateTime actualWorkDateTime = AttendanceForm.toActualWorkDateTime("2017-10-20", "9:00", "24:00", "60", "0");

        TimeRecord expectTimeRecord = new TimeRecord(employeeNumber, actualWorkDateTime);
        timeRecordRecordService.registerTimeRecord(expectTimeRecord);

        Attendance attendance = attendanceQueryService.findAttendance(employee, new WorkMonth("2017-10"));
        assertAll(
                () -> assertEquals(attendance.month().toStringWithUnit(), "2017年10月"),
                () -> assertEquals(attendance.listWorkDates().size(), 31),
                () -> assertTrue(attendance.statusOf(new WorkDate("2017-10-20")).isWork())
        );
    }
}
