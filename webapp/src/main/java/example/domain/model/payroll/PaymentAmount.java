package example.domain.model.payroll;

import example.domain.model.wage.HourlyWage;
import example.domain.type.amount.Amount;
import example.domain.type.time.QuarterHour;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 支払い金額
 */
public class PaymentAmount {

    Amount value;

    public PaymentAmount(BigDecimal value) {
        this(new Amount(value.intValue()));
    }

    public PaymentAmount(Amount value) {
        this.value = value;
    }

    public static PaymentAmount from(HourlyWage wage, QuarterHour workTime) {
        BigDecimal hour = workTime.bigDecimalValue();
        return new PaymentAmount(hour.multiply(new BigDecimal(wage.value().value())));
    }

    PaymentAmount add(PaymentAmount paymentAmount) {
        return new PaymentAmount(this.value.add(paymentAmount.value));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public PaymentAmount addAll(PaymentAmount... paymentAmounts) {
        Amount amount = value;
        Amount[] amounts = Arrays.stream(paymentAmounts).map(paymentAmount -> paymentAmount.value).toArray(Amount[]::new);
        return new PaymentAmount(amount.addAll(amounts));
    }
}
