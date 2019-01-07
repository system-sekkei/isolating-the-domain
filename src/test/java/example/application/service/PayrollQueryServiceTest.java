package example.application.service;

import example.Application;
import example.application.service.contract.ContractRecordService;
import example.application.service.payroll.PayrollQueryService;
import example.application.service.worker.WorkerQueryService;
import example.application.service.worker.WorkerRecordService;
import example.application.service.workrecord.WorkRecordRecordService;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.WageCondition;
import example.domain.model.legislation.MidnightExtraRate;
import example.domain.model.legislation.OverTimeExtraRate;
import example.domain.model.payroll.Payroll;
import example.domain.model.timerecord.*;
import example.domain.model.worker.*;
import example.domain.type.date.Date;
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
    WorkRecordRecordService workRecordRecordService;

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
            Payroll payroll = sut.payroll(worker, new WorkMonth("2018-11"));
            assertEquals("0", payroll.totalPaymentAmount().toString());
        }

        {
            WageCondition wageCondition = new WageCondition(new HourlyWage(1000), OverTimeExtraRate.legal(), MidnightExtraRate.legal());
            contractRecordService.registerHourlyWage(workerNumber, new Date("2018-11-20"), wageCondition);

            TimeRecord timeRecord = new TimeRecord(
                    workerNumber, new WorkDate(new Date("2018-11-20")),
                    new ActualWorkTime(new TimeRange(new StartTime(new ClockTime("09:00")), new EndTime(new ClockTime("10:00"))), new DaytimeBreakTime(new Minute("0")), new MidnightBreakTime(new Minute("0")))
            );
            workRecordRecordService.registerWorkRecord(timeRecord);

            Payroll payroll = sut.payroll(worker, new WorkMonth("2018-11"));
            assertEquals("1,000", payroll.totalPaymentAmount().toString());
        }

        {
            TimeRecord timeRecord = new TimeRecord(
                    workerNumber, new WorkDate(new Date("2018-11-25")),
                    new ActualWorkTime(new TimeRange(new StartTime(new ClockTime("22:00")), new EndTime(new ClockTime("23:00"))), new DaytimeBreakTime(new Minute("0")), new MidnightBreakTime(new Minute("0")))
            );
            workRecordRecordService.registerWorkRecord(timeRecord);

            Payroll payroll = sut.payroll(worker, new WorkMonth("2018-11"));
            assertEquals("2,350", payroll.totalPaymentAmount().toString());
        }

        {
            WageCondition wageCondition = new WageCondition(new HourlyWage(2000), OverTimeExtraRate.legal(), MidnightExtraRate.legal());
            contractRecordService.registerHourlyWage(workerNumber, new Date("2018-11-25"), wageCondition);

            Payroll payroll = sut.payroll(worker, new WorkMonth("2018-11"));
            assertEquals("3,700", payroll.totalPaymentAmount().toString());
        }
    }
}