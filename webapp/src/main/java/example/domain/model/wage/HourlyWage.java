package example.domain.model.wage;

import example.domain.type.amount.Amount;
import example.domain.type.time.QuarterHour;

/**
 * 時給
 */
public class HourlyWage {
    Amount value;

    @Deprecated
    public HourlyWage() {
    }

    public HourlyWage(Amount value) {
        this.value = value;
    }

    public HourlyWage(Integer value) {
        this(new Amount(value));
    }

    public static HourlyWage disable() {
        return new HourlyWage(Integer.MIN_VALUE);
    }

    @Override
    public String toString() {
        if (value.value() == disable().value().value()) {
            return "";
        }
        return value.toString();
    }

    public Amount value() {
        return value;
    }

    public Amount multiply(QuarterHour time) {
        return new Amount(value.value() * time.minute().toInt());
    }
}
