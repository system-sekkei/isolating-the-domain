package example.domain.model.payroll;

import example.domain.model.contract.HourlyWage;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 賃金
 */
public class Wage {
    BigDecimal value;

    public Wage(BigDecimal value) {
        this.value = value;
    }

    public Wage() {
        this(BigDecimal.ZERO);
    }

    public static Wage of(WorkHours workHours, HourlyWage hourlyWage) {
        BigDecimal value = workHours.value().multiply(new BigDecimal(hourlyWage.value()));
        return new Wage(value);
    }

    public Wage add(Wage other) {
        return new Wage(value.add(other.value));
    }

    public String toString() {
        return new DecimalFormat("#,##0").format(value.intValue());
    }
}
