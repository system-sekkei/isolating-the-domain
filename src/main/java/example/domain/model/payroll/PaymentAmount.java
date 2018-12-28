package example.domain.model.payroll;

import example.domain.model.attendance.WorkTimeSummary;
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

    public static PaymentAmount from(WorkTimeSummary workTimeSummary, WageCondition wageCondition) {
        return new PaymentWorkTime(workTimeSummary.totalWorkTime()).multiply(wageCondition.baseHourlyWage())
                .add(new PaymentWorkTime(workTimeSummary.totalOverWorkTime()).multiply(wageCondition.overTimeHourlyExtraWage().value()))
                .add(new PaymentWorkTime(workTimeSummary.totalMidnightWorkTime()).multiply(wageCondition.midnightHourlyExtraWage().value()));
    }

    PaymentAmount add(PaymentAmount paymentAmount) {
        return new PaymentAmount(this.value.add(paymentAmount.value));
    }

    @Override
    public String toString() {
        return new DecimalFormat("#,##0").format(value.intValue());
    }
}
