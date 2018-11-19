package example.domain.model.payroll;

import example.domain.model.contract.HourlyWage;

import java.math.BigDecimal;

/**
 * 賃金
 */
public class Wage {
    BigDecimal value;

    public Wage(BigDecimal value) {
        this.value = value;
    }

    public Wage(HourlyWage hourlyWage, WorkHours workHours) {
        // TODO まるめ
        this(BigDecimal.valueOf(hourlyWage.value()).multiply(workHours.value()));
    }

    public static Wage of(WorkHours workHours, HourlyWage hourlyWage) {
        BigDecimal value = workHours.value().multiply(new BigDecimal(hourlyWage.value()));
//        System.out.println(value);
        return new Wage(value);
    }

    public Wage add(Wage other) {
        return new Wage(value.add(other.value));
    }
}
