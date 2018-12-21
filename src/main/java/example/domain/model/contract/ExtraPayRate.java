package example.domain.model.contract;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 割増率（％）
 */
public class ExtraPayRate {
    Integer value;

    public ExtraPayRate(Integer value) {
        this.value = value;
    }

    HourlyWage apply(HourlyWage hourlyWage) {
        BigDecimal value = BigDecimal.valueOf(this.value).divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY);
        BigDecimal hourlyWageValue = BigDecimal.valueOf(hourlyWage.value());
        BigDecimal calculatedHourlyWage = hourlyWageValue.multiply(value).setScale(0, RoundingMode.DOWN);
        return new HourlyWage(calculatedHourlyWage.intValueExact());
    }
}
