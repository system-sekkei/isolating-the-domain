package example.domain.model.contract;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 割増率（％）
 */
public class ExtraPayRate {
    BigDecimal value;

    public ExtraPayRate(Integer value) {
        this.value = BigDecimal.valueOf(value).divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY);
    }

    HourlyWage apply(HourlyWage hourlyWage) {
        BigDecimal hourlyWageValue = BigDecimal.valueOf(hourlyWage.value());
        BigDecimal calculatedHourlyWage = hourlyWageValue.multiply(value);
        return new HourlyWage(calculatedHourlyWage.intValueExact());
    }
}
