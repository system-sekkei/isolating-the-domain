package example.domain.model.wage;

import example.domain.model.legislation.ExtraPayRate;
import example.domain.model.legislation.MidnightExtraRate;
import example.domain.model.legislation.OverTimeExtraRate;
import example.domain.type.amount.Amount;
import example.domain.type.amount.RoundingMode;

import java.math.BigDecimal;

/**
 * 時給
 */
public class HourlyWage {

    Amount value;

    public HourlyWage(Integer value) {
        this(new Amount(value));
    }

    public HourlyWage(String value) {
        this(new Amount(value));
    }

    HourlyWage(Amount value) {
        this.value = value;
    }

    public static HourlyWage disable() {
        return new HourlyWage(Integer.MIN_VALUE);
    }

    public BigDecimal value() {
        return value.value();
    }

    @Override
    public String toString() {
        if (value.value().equals(disable().value())) {
            return "";
        }
        return value.toString();
    }

    public OverTimeHourlyExtraWage overTimeHourlyExtraWage(OverTimeExtraRate overTimeExtraRate) {
        return new OverTimeHourlyExtraWage(withExtraPayRate(overTimeExtraRate.value()));
    }

    public MidnightHourlyExtraWage midnightHourlyExtraWage(MidnightExtraRate midnightExtraRate) {
        return new MidnightHourlyExtraWage(withExtraPayRate(midnightExtraRate.value()));
    }

    HourlyWage withExtraPayRate(ExtraPayRate extraPayRate) {
        return new HourlyWage(value.multiply(extraPayRate.value(), RoundingMode.切り捨て));
    }
}
