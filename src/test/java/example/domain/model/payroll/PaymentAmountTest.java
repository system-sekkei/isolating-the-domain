package example.domain.model.payroll;

import example.domain.model.workrecord.WorkRecord;
import example.domain.model.workrecord.WorkRecords;
import example.domain.model.workrecord.WorkDate;
import example.domain.model.workrecord.*;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.WageCondition;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentAmountTest {
    @DisplayName("作業時間と時給で賃金計算が行えること")
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
    void wage(String begin, String end, int breakMinute, int midnightBreakMinute, int hourlyWage, int expected) {
        WorkRecord workRecord = new WorkRecord(
                new WorkerNumber(), new WorkDate("2018-11-04"),
                new WorkTimeRecord(
                        new WorkTimeRange(new WorkStartTime(begin), new WorkEndTime(end)),
                        new DaytimeBreakTime(new Minute(breakMinute)),
                        new MidnightBreakTime(new Minute(midnightBreakMinute)))
        );
        WageCondition wageCondition = new WageCondition(new HourlyWage(hourlyWage));
        WorkRecords workRecords = new WorkRecords(Collections.singletonList(workRecord));

        PaymentAmount paymentAmount = new PaymentAmount(workRecords.summarize(), wageCondition);

        assertEquals(expected, paymentAmount.value.intValue());
    }
}
