package example.domain.model.payroll;

import example.domain.model.attendance.AllMidnightWorkTime;
import example.domain.model.attendance.AllOverWorkTime;
import example.domain.model.attendance.AllWorkTime;
import example.domain.model.attendance.WorkTimeSummary;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.MidnightHourlyExtraWage;
import example.domain.model.contract.OverTimeHourlyExtraWage;
import example.domain.model.contract.WageCondition;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 支払い金額
 */
public class PaymentAmount {

    BigDecimal value;

    public PaymentAmount(BigDecimal value) {
        this.value = value;
    }

    PaymentAmount(AllWorkTime totalWorkTime, HourlyWage hourlyWage) {
        this(WorkHours.of(totalWorkTime.minute()).multiply(hourlyWage));
    }

    PaymentAmount(AllOverWorkTime overWorkTime, OverTimeHourlyExtraWage overTimeHourlyExtraWage) {
        this(WorkHours.of(overWorkTime.minute()).multiply(overTimeHourlyExtraWage.value()));
    }

    PaymentAmount(AllMidnightWorkTime midnightWorkTime, MidnightHourlyExtraWage midnightHourlyExtraWage) {
        this(WorkHours.of(midnightWorkTime.minute()).multiply(midnightHourlyExtraWage.value()));
    }

    public static PaymentAmount from(WorkTimeSummary workTimeSummary, WageCondition wageCondition) {
        return new PaymentAmount(workTimeSummary.totalWorkTime(), wageCondition.baseHourlyWage())
                .add(new PaymentAmount(workTimeSummary.overWorkTime(), wageCondition.overTimeHourlyExtraWage()))
                .add(new PaymentAmount(workTimeSummary.midnightWorkTime(), wageCondition.midnightHourlyExtraWage()));
    }

    PaymentAmount add(PaymentAmount paymentAmount) {
        return new PaymentAmount(this.value.add(paymentAmount.value));
    }

    @Override
    public String toString() {
        return new DecimalFormat("#,##0").format(value.intValue());
    }
}
