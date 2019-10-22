package example.domain.model.payroll;

import example.domain.model.timerecord.evaluation.ActualWorkDateTime;
import example.domain.model.wage.WageCondition;
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


    public PaymentAmount(ActualWorkDateTime actualWorkDateTime, WageCondition wageCondition) {
        PaymentAmount workTimeAmount = new PaymentWorkTime(actualWorkDateTime.workTime()).multiply(wageCondition.baseHourlyWage());
        PaymentAmount overTimeExtraAmount = new PaymentWorkTime(actualWorkDateTime.overWorkTime()).multiply(wageCondition.overTimeHourlyExtraWage().value());
        PaymentAmount nightExtraAmount = new PaymentWorkTime(actualWorkDateTime.nightWorkTime()).multiply(wageCondition.nightHourlyExtraWage().value());
        this.value = workTimeAmount.value.add(overTimeExtraAmount.value).add(nightExtraAmount.value);
    }

    PaymentAmount add(PaymentAmount paymentAmount) {
        return new PaymentAmount(this.value.add(paymentAmount.value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
