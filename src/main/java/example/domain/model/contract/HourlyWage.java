package example.domain.model.contract;

import example.domain.model.legislation.ExtraPayRate;
import example.domain.model.legislation.MidnightExtraRate;
import example.domain.model.legislation.OverTimeExtraRate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 時給
 */
public class HourlyWage {
    Integer value;

    public HourlyWage(Integer value) {
        this.value = value;
    }

    public HourlyWage(String value) {
        this.value = Integer.parseInt(value);
    }

    public Integer value() {
        return value;
    }

    public String toString() {
        return value.toString();
    }

    public OverTimeHourlyExtraWage overTimeHourlyExtraWage(OverTimeExtraRate overTimeExtraRate) {
        return new OverTimeHourlyExtraWage(withExtraPayRate(overTimeExtraRate.value()));
    }

    public MidnightHourlyExtraWage midnightHourlyExtraWage(MidnightExtraRate midnightExtraRate) {
        return new MidnightHourlyExtraWage(withExtraPayRate(midnightExtraRate.value()));
    }

    HourlyWage withExtraPayRate(ExtraPayRate extraPayRate) {
        BigDecimal rate = extraPayRate.rate();
        BigDecimal hourlyWageValue = BigDecimal.valueOf(value);
        BigDecimal calculatedHourlyWage = hourlyWageValue.multiply(rate).setScale(0, RoundingMode.DOWN);
        return new HourlyWage(calculatedHourlyWage.intValueExact());
    }
}
