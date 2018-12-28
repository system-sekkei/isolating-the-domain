package example.domain.model.payroll;

import example.domain.model.attendance.TotalMidnightWorkTime;
import example.domain.model.attendance.TotalOverWorkTime;
import example.domain.model.attendance.TotalWorkTime;
import example.domain.model.contract.HourlyWage;
import example.domain.type.time.QuarterHour;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 支払い対象時間
 */
public class PaymentWorkTime {
    QuarterHour value;

    PaymentWorkTime(TotalWorkTime totalWorkTime) {
        this(totalWorkTime.quarterHour());
    }

    PaymentWorkTime(TotalOverWorkTime totalOverWorkTime) {
        this(totalOverWorkTime.quarterHour());
    }

    PaymentWorkTime(TotalMidnightWorkTime totalMidnightWorkTime) {
        this(totalMidnightWorkTime.quarterHour());
    }

    private PaymentWorkTime(QuarterHour value) {
        this.value = value;
    }

    public PaymentAmount multiply(HourlyWage hourlyWage) {
        BigDecimal hour = new BigDecimal(value.minute().value()).divide(BigDecimal.valueOf(60), 2, RoundingMode.UNNECESSARY);
        return new PaymentAmount(hour.multiply(BigDecimal.valueOf(hourlyWage.value())));
    }
}
