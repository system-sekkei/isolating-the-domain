package example.application.service;

import example.Application;
import example.application.service.contract.ContractRecordService;
import example.application.service.employee.EmployeeRecordService;
import example.application.coordinator.payroll.PayrollQueryCoordinator;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordRecordService;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.wage.HourlyWage;
import example.domain.model.wage.WageCondition;
import example.domain.model.legislation.MidnightExtraRate;
import example.domain.model.legislation.OverTimeExtraRate;
import example.domain.model.payroll.Payroll;
import example.domain.model.timerecord.*;
import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.breaktime.MidnightBreakTime;
import example.domain.model.employee.*;
import example.domain.type.date.Date;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
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
            WageCondition wageCondition = new WageCondition(new HourlyWage(1000), OverTimeExtraRate.legal(), MidnightExtraRate.legal());
            contractRecordService.registerHourlyWage(employeeNumber, new Date("2018-11-20"), wageCondition);

            TimeRecord timeRecord = new TimeRecord(
                    employeeNumber, new WorkDate(new Date("2018-11-20")),
                    new ActualWorkTime(new TimeRange(new StartTime(new ClockTime("09:00")), new EndTime(new ClockTime("10:00"))), new DaytimeBreakTime(new Minute("0")), new MidnightBreakTime(new Minute("0")))
            );
            timeRecordRecordService.registerTimeRecord(timeRecord);

            Payroll payroll = sut.payroll(employee, new WorkMonth("2018-11"));
            assertEquals("1,000円", payroll.totalPayment().toString());
        }

        {
            TimeRecord timeRecord = new TimeRecord(
                    employeeNumber, new WorkDate(new Date("2018-11-25")),
                    new ActualWorkTime(new TimeRange(new StartTime(new ClockTime("22:00")), new EndTime(new ClockTime("23:00"))), new DaytimeBreakTime(new Minute("0")), new MidnightBreakTime(new Minute("0")))
            );
            timeRecordRecordService.registerTimeRecord(timeRecord);

            Payroll payroll = sut.payroll(employee, new WorkMonth("2018-11"));
            assertEquals("2,350円", payroll.totalPayment().toString());
        }

        {
            WageCondition wageCondition = new WageCondition(new HourlyWage(2000), OverTimeExtraRate.legal(), MidnightExtraRate.legal());
            contractRecordService.registerHourlyWage(employeeNumber, new Date("2018-11-25"), wageCondition);

            Payroll payroll = sut.payroll(employee, new WorkMonth("2018-11"));
            assertEquals("3,700円", payroll.totalPayment().toString());
        }
    }
}