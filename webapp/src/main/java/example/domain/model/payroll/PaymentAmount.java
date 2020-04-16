package example.domain.model.payroll;

import example.domain.model.attendance.PayableWork;
import example.domain.model.contract.WageCondition;
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

    PaymentAmount add(PaymentAmount paymentAmount) {
        return new PaymentAmount(this.value.add(paymentAmount.value));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public PaymentAmount addConsiderationAmount(PayableWork payableWork, WageCondition wageCondition) {
        return this.add(new PaymentWorkTime(payableWork.workTime()).multiply(wageCondition.baseHourlyWage().value()))
                .add(new PaymentWorkTime(payableWork.overWorkTime()).multiply(wageCondition.overTimeHourlyExtraWage().value()))
                .add(new PaymentWorkTime(payableWork.nightWorkTime()).multiply(wageCondition.nightHourlyExtraWage().value()));
    }
}
