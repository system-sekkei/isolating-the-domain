package example.application.service.payroll;

import example.Application;
import example.application.service.attendance.AttendanceRecordService;
import example.application.service.contract.ContractRecordService;
import example.application.service.worker.WorkerQueryService;
import example.application.service.worker.WorkerRecordService;
import example.domain.model.attendance.*;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.WageCondition;
import example.domain.model.labour_standards_law.MidnightExtraRate;
import example.domain.model.labour_standards_law.OverTimeExtraRate;
import example.domain.model.payroll.Payroll;
import example.domain.model.worker.*;
import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
class PayrollQueryServiceTest {

    @Autowired
    PayrollQueryService sut;

    @Autowired
    ContractRecordService contractRecordService;
    @Autowired
    WorkerRecordService workerRecordService;
    @Autowired
    WorkerQueryService workerQueryService;
    @Autowired
    AttendanceRecordService attendanceRecordService;

    @Test
    void test() {
        WorkerNumber workerNumber = workerRecordService.prepareNewContract();
        // この時点で呼んだらエラーにしなきゃならない。このテストのスコープからは外れるが。
        // workerRecordService.inspireContract(workerNumber);

        workerRecordService.registerMailAddress(workerNumber, new MailAddress(""));
        workerRecordService.registerName(workerNumber, new Name(""));
        workerRecordService.registerPhoneNumber(workerNumber, new PhoneNumber(""));
        workerRecordService.inspireContract(workerNumber);

        Worker worker = workerQueryService.choose(workerNumber);

        {
            Payroll payroll = sut.getPayroll(worker, new YearMonth("2018-11"));
            assertEquals("0", payroll.wage().toString());
        }

        {
            WageCondition wageCondition = new WageCondition(new HourlyWage(1000), OverTimeExtraRate.legal(), MidnightExtraRate.legal());
            contractRecordService.registerHourlyWage(workerNumber, new Date("2018-11-20"), wageCondition);

            Attendance attendance = new Attendance(
                    new WorkDay(new Date("2018-11-20")),
                    new WorkStartTime(new ClockTime("09:00")),
                    new WorkEndTime(new ClockTime("10:00")),
                    new NormalBreakTime(new Minute("0")),
                    new MidnightBreakTime(new Minute("0"))
            );
            attendanceRecordService.registerAttendance(workerNumber, attendance);

            Payroll payroll = sut.getPayroll(worker, new YearMonth("2018-11"));
            assertEquals("1,000", payroll.wage().toString());
        }

        {
            Attendance attendance = new Attendance(
                    new WorkDay(new Date("2018-11-25")),
                    new WorkStartTime(new ClockTime("22:00")),
                    new WorkEndTime(new ClockTime("23:00")),
                    new NormalBreakTime(new Minute("0")),
                    new MidnightBreakTime(new Minute("0"))
            );
            attendanceRecordService.registerAttendance(workerNumber, attendance);

            Payroll payroll = sut.getPayroll(worker, new YearMonth("2018-11"));
            assertEquals("2,350", payroll.wage().toString());
        }

        {
            WageCondition wageCondition = new WageCondition(new HourlyWage(2000), OverTimeExtraRate.legal(), MidnightExtraRate.legal());
            contractRecordService.registerHourlyWage(workerNumber, new Date("2018-11-25"), wageCondition);

            Payroll payroll = sut.getPayroll(worker, new YearMonth("2018-11"));
            assertEquals("3,700", payroll.wage().toString());
        }
    }
}