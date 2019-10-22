package example.application.service;

import example.application.coordinator.payroll.PayrollQueryCoordinator;
import example.application.service.contract.ContractRecordService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.employee.EmployeeRecordService;
import example.application.service.timerecord.TimeRecordRecordService;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.*;
import example.domain.model.legislation.NightExtraRate;
import example.domain.model.legislation.OverTimeExtraRate;
import example.domain.model.payroll.Payroll;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.wage.HourlyWage;
import example.domain.model.wage.WageCondition;
import example.domain.type.date.Date;
import example.domain.type.time.InputTime;
import example.presentation.controller.timerecord.AttendanceForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PayrollQueryCoordinatorTest {

    @Autowired
    PayrollQueryCoordinator sut;

    @Autowired
    ContractRecordService contractRecordService;
    @Autowired
    EmployeeRecordService employeeRecordService;
    @Autowired
    EmployeeQueryService employeeQueryService;
    @Autowired
    TimeRecordRecordService timeRecordRecordService;

    @Test
    void test() {
        EmployeeNumber employeeNumber = employeeRecordService.prepareNewContract();
        // この時点で呼んだらエラーにしなきゃならない。このテストのスコープからは外れるが。
        // employeeRecordService.inspireContract(employeeNumber);

        employeeRecordService.registerMailAddress(employeeNumber, new MailAddress(""));
        employeeRecordService.registerName(employeeNumber, new Name(""));
        employeeRecordService.registerPhoneNumber(employeeNumber, new PhoneNumber(""));
        employeeRecordService.inspireContract(employeeNumber);

        Employee employee = employeeQueryService.choose(employeeNumber);

        {
            Payroll payroll = sut.payroll(employee, new WorkMonth("2018-11"));
            assertEquals("0円", payroll.totalPayment().toString());
        }

        {
            WageCondition wageCondition = new WageCondition(new HourlyWage(1000), OverTimeExtraRate.legal(), NightExtraRate.legal());
            contractRecordService.registerHourlyWage(employeeNumber, new Date("2018-11-20"), wageCondition);

            TimeRecord timeRecord = new TimeRecord(
                    employeeNumber,
                    AttendanceForm.toActualWorkDateTime("2018-11-20", "9:00", "10:00", "0", "0")
            );
            timeRecordRecordService.registerTimeRecord(timeRecord);

            Payroll payroll = sut.payroll(employee, new WorkMonth("2018-11"));
            assertEquals("1,000円", payroll.totalPayment().toString());
        }

        {
            Date date = new Date("2018-11-25");
            InputTime startTime = new InputTime(22, 0);
            InputTime endTime = new InputTime(23, 0);
            TimeRecord timeRecord = new TimeRecord(
                    employeeNumber,
                    AttendanceForm.toActualWorkDateTime("2018-11-25", "22:00", "23:00", "0", "0")
            );
            timeRecordRecordService.registerTimeRecord(timeRecord);

            Payroll payroll = sut.payroll(employee, new WorkMonth("2018-11"));
            assertEquals("2,350円", payroll.totalPayment().toString());
        }

        {
            WageCondition wageCondition = new WageCondition(new HourlyWage(2000), OverTimeExtraRate.legal(), NightExtraRate.legal());
            contractRecordService.registerHourlyWage(employeeNumber, new Date("2018-11-25"), wageCondition);

            Payroll payroll = sut.payroll(employee, new WorkMonth("2018-11"));
            assertEquals("3,700円", payroll.totalPayment().toString());
        }
    }
}