package example.domain.model.payroll;

import example.domain.model.wage.HourlyWage;
import example.domain.model.timerecord.MidnightWorkTime;
import example.domain.model.timerecord.OverWorkTime;
import example.domain.model.timerecord.WorkTime;
import example.domain.type.time.QuarterHour;

import java.math.BigDecimal;

/**
 * 支払い対象時間
 */
public class PaymentWorkTime {
    QuarterHour value;

    PaymentWorkTime(MidnightWorkTime midnightWorkTime) {
        this(midnightWorkTime.quarterHour());
    }

    PaymentWorkTime(OverWorkTime overWorkTime) {
        this(overWorkTime.quarterHour());
    }

    PaymentWorkTime(WorkTime workTime) {
        this(workTime.quarterHour());
    }

    private PaymentWorkTime(QuarterHour value) {
        this.value = value;
    }

    public PaymentAmount multiply(HourlyWage hourlyWage) {
        BigDecimal hour = value.bigDecimalValue();
        return new PaymentAmount(hour.multiply(hourlyWage.value()));
    }
}
