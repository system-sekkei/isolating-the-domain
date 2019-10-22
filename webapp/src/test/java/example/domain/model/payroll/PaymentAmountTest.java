package example.domain.model.payroll;

import example.domain.model.attendance.PayableWork;
import example.domain.model.timerecord.evaluation.ActualWorkDateTime;
import example.domain.model.wage.HourlyWage;
import example.domain.model.wage.WageCondition;
import example.presentation.controller.timerecord.AttendanceForm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentAmountTest {

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
    void 割増含めた賃金計算ができる(String begin, String end, String breakMinute, String nightBreakMinute, int hourlyWage, int expected) {
        ActualWorkDateTime actualWorkDateTime = AttendanceForm.toActualWorkDateTime("2018-11-25", begin, end, breakMinute, nightBreakMinute);
        PayableWork payableWork = new PayableWork(actualWorkDateTime);
        WageCondition wageCondition = new WageCondition(new HourlyWage(hourlyWage));

        PaymentAmount paymentAmount = PaymentAmount.calculate(payableWork, wageCondition);

        assertEquals(expected, paymentAmount.value.value().intValue());
    }
}
