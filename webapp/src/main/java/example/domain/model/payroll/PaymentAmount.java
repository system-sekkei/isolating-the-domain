package example.domain.model.payroll;

import example.domain.model.wage.WageCondition;
import example.domain.model.timerecord.ActualWorkTime;
import example.domain.type.amount.Amount;

import java.math.BigDecimal;

/**
 * 支払い金額
 */
public class PaymentAmount {

    Amount value;

    public PaymentAmount(BigDecimal value) {
        this(new Amount(value));
    }

    public PaymentAmount(Amount value) {
        this.value = value;
    }


    public PaymentAmount(ActualWorkTime actualWorkTime, WageCondition wageCondition) {
        PaymentAmount workTimeAmount = new PaymentWorkTime(actualWorkTime.workTime()).multiply(wageCondition.baseHourlyWage());
        PaymentAmount overTimeExtraAmount = new PaymentWorkTime(actualWorkTime.overWorkTime()).multiply(wageCondition.overTimeHourlyExtraWage().value());
        PaymentAmount midnightExtraAmount = new PaymentWorkTime(actualWorkTime.midnightWorkTime()).multiply(wageCondition.midnightHourlyExtraWage().value());
        this.value = workTimeAmount.value.add(overTimeExtraAmount.value).add(midnightExtraAmount.value);
    }

    PaymentAmount add(PaymentAmount paymentAmount) {
        return new PaymentAmount(this.value.add(paymentAmount.value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
