package example.domain.model.payroll;

import example.domain.model.attendance.PayableWork;
import example.domain.model.contract.wage.WageCondition;
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
                // TODO:
//                .add(new PaymentWorkTime(payableWork.overLegalMoreThan60HoursWorkTime()).multiply(wageCondition.overLegalMoreThan60HoursHourlyExtraWage().value()))
//                .add(new PaymentWorkTime(payableWork.overLegalUpTo60HoursWorkTime()).multiply(wageCondition.overLegalUpTo60HoursHourlyExtraWage().value()))
//                .add(new PaymentWorkTime(payableWork.overPrescribedWorkTime()).multiply(wageCondition.overPrescribedHourlyExtraWage().value()))
//                .add(new PaymentWorkTime(payableWork.legalDaysOffWorkTime()).multiply(wageCondition.legalDaysOffHourlyExtraWage().value()))
//                .add(new PaymentWorkTime(payableWork.nonLegalDaysOffWorkTime()).multiply(wageCondition.nonLegalDaysOffHourlyExtraWage().value()))
                .add(new PaymentWorkTime(payableWork.nightWorkTime()).multiply(wageCondition.nightHourlyExtraWage().value()));
    }
}
