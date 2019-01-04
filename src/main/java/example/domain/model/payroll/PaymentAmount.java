package example.domain.model.payroll;

import example.domain.model.contract.WageCondition;
import example.domain.model.timerecord.WorkTimeRecord;

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

    public PaymentAmount(WorkTimeRecord workTimeRecord, WageCondition wageCondition) {
        PaymentAmount workTimeAmount = new PaymentWorkTime(workTimeRecord.workTime()).multiply(wageCondition.baseHourlyWage());
        PaymentAmount overTimeExtraAmount = new PaymentWorkTime(workTimeRecord.overWorkTime()).multiply(wageCondition.overTimeHourlyExtraWage().value());
        PaymentAmount midnightExtraAmount = new PaymentWorkTime(workTimeRecord.midnightWorkTime()).multiply(wageCondition.midnightHourlyExtraWage().value());
        this.value = workTimeAmount.value.add(overTimeExtraAmount.value).add(midnightExtraAmount.value);
    }

    PaymentAmount add(PaymentAmount paymentAmount) {
        return new PaymentAmount(this.value.add(paymentAmount.value));
    }

    @Override
    public String toString() {
        return new DecimalFormat("#,##0").format(value.intValue());
    }
}
