package example.domain.model.payroll;

import example.domain.model.attendance.WorkTimeSummary;
import example.domain.model.attendance.worktimerecord.MidnightWorkTime;
import example.domain.model.attendance.worktimerecord.OverWorkTime;
import example.domain.model.attendance.worktimerecord.TotalWorkTime;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.MidnightHourlyExtraWage;
import example.domain.model.contract.OverTimeHourlyExtraWage;
import example.domain.model.contract.WageCondition;

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

    Wage(TotalWorkTime totalWorkTime, HourlyWage hourlyWage) {
        this(WorkHours.of(totalWorkTime.minute()).multiply(hourlyWage));
    }

    Wage(OverWorkTime overWorkTime, OverTimeHourlyExtraWage overTimeHourlyExtraWage) {
        this(WorkHours.of(overWorkTime.minute()).multiply(overTimeHourlyExtraWage.value()));
    }

    Wage(MidnightWorkTime midnightWorkTime, MidnightHourlyExtraWage midnightHourlyExtraWage) {
        this(WorkHours.of(midnightWorkTime.minute()).multiply(midnightHourlyExtraWage.value()));
    }

    public static Wage from(WorkTimeSummary workTimeSummary, WageCondition wageCondition) {
        return new Wage(workTimeSummary.totalWorkTime(), wageCondition.baseHourlyWage())
                .add(new Wage(workTimeSummary.overWorkTime(), wageCondition.overTimeHourlyExtraWage()))
                .add(new Wage(workTimeSummary.midnightWorkTime(), wageCondition.midnightHourlyExtraWage()));
    }

    public static Wage invalid() {
        return new Wage(null);
    }

    Wage add(Wage wage) {
        return new Wage(this.value.add(wage.value));
    }

    public String toString() {
        if (this.value == null) {
            return "";
        }
        return new DecimalFormat("#,##0").format(value.intValue());
    }
}
