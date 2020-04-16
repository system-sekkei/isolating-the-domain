package example.domain.model.wage;

import example.domain.model.legislation.ExtraPayRate;
import example.domain.model.contract.NightExtraRate;
import example.domain.model.contract.OverLegalTimeExtraRate;
import example.domain.type.amount.Amount;
import example.domain.type.amount.RoundingMode;

import java.math.BigDecimal;

/**
 * 時給
 */
public class HourlyWage {

    Amount value;

    @Deprecated
    public HourlyWage() {
    }

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

    public OverTimeHourlyExtraWage overTimeHourlyExtraWage(OverLegalTimeExtraRate overLegalTimeExtraRate) {
        return new OverTimeHourlyExtraWage(withExtraPayRate(overLegalTimeExtraRate.value()));
    }

    public NightHourlyExtraWage nightHourlyExtraWage(NightExtraRate nightExtraRate) {
        return new NightHourlyExtraWage(withExtraPayRate(nightExtraRate.value()));
    }

    HourlyWage withExtraPayRate(ExtraPayRate extraPayRate) {
        return new HourlyWage(value.multiply(extraPayRate.value(), RoundingMode.切り捨て));
    }
}
