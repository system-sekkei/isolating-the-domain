package example.domain.model.payroll;

import example.domain.model.attendance.PayableWork;
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


    public PaymentAmount(PayableWork payableWork, WageCondition wageCondition) {
        PaymentAmount workTimeAmount = new PaymentWorkTime(payableWork.workTime()).multiply(wageCondition.baseHourlyWage());
        PaymentAmount overTimeExtraAmount = new PaymentWorkTime(payableWork.overWorkTime()).multiply(wageCondition.overTimeHourlyExtraWage().value());
        PaymentAmount nightExtraAmount = new PaymentWorkTime(payableWork.nightWorkTime()).multiply(wageCondition.nightHourlyExtraWage().value());
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
