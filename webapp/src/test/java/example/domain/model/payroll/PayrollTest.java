package example.domain.model.payroll;

import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.TimeRecords;
import example.domain.model.contract.Contract;
import example.domain.model.contract.ContractCondition;
import example.domain.model.contract.ContractConditions;
import example.domain.model.contract.wage.BaseHourlyWage;
import example.domain.model.contract.wage.WageCondition;
import example.domain.model.timerecord.evaluation.ActualWorkDateTime;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.presentation.controller.timerecord.AttendanceForm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayrollTest {
    @ParameterizedTest
    @CsvSource({
            // 通常
            "09:00, 10:00, 0, 0, 1000, 1000",
            // 深夜
            "23:00, 24:00, 0, 0, 1000, 1350",
            // 深夜（早朝）
            "01:00, 03:00, 0, 0, 1000, 2700",
            // 通常＋深夜＋休憩
            "20:00, 24:00, 30, 60, 1000, 2850",
            // 通常10時間（超過2時間）
            "09:00, 19:00, 0, 0, 1000, 10500",
            // 通常13時間＋深夜1時間（超過6時間）
            "8:00, 24:00, 60, 60, 1000, 15850",
            // 通常17時間＋深夜7時間（超過16時間）
            "0:00, 24:00, 0, 0, 1000, 30450"
    })
    static void 割増含めた賃金計算ができる(String begin, String end, String breakMinute, String nightBreakMinute, int hourlyWage, int expected) {
        ActualWorkDateTime actualWorkDateTime = AttendanceForm.toActualWorkDateTime("2018-11-25", begin, end, breakMinute, nightBreakMinute);
        WageCondition wageCondition = new WageCondition(new BaseHourlyWage(hourlyWage));

        ContractConditions contractConditions = new ContractConditions(List.of(new ContractCondition(null, wageCondition)));

        Contract contract = new Contract(null, contractConditions);
        TimeRecord timeRecord = new TimeRecord(null, actualWorkDateTime, null);
        TimeRecords timeRecords = new TimeRecords(List.of(timeRecord));
        Attendance attendance = new Attendance(null, timeRecords);
        Payroll payroll = new Payroll(contract, attendance);

        PaymentAmount paymentAmount = payroll.totalPayment();

        assertEquals(expected, paymentAmount.value.value().intValue());
    }
}