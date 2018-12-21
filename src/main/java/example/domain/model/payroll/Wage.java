package example.domain.model.payroll;

import example.domain.model.attendance.MidnightWorkTime;
import example.domain.model.attendance.OverWorkTime;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.MidnightHourlyExtraWage;
import example.domain.model.contract.OverTimeHourlyExtraWage;

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

    public Wage add(OverWorkTime overTime, OverTimeHourlyExtraWage overTimeAdditionalHourlyWage) {
        return this.add(Wage.of(WorkHours.of(overTime), overTimeAdditionalHourlyWage.value()));
    }

    public Wage add(MidnightWorkTime midnightWorkTime, MidnightHourlyExtraWage midnightAdditionalHourlyWage) {
        return this.add(Wage.of(WorkHours.of(midnightWorkTime), midnightAdditionalHourlyWage.value()));
    }
}
