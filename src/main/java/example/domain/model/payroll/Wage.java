package example.domain.model.payroll;

import example.domain.model.attendance.MidnightWorkTime;
import example.domain.model.attendance.OverWorkTime;
import example.domain.model.attendance.WorkTime;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.MidnightHourlyExtraWage;
import example.domain.model.contract.OverTimeHourlyExtraWage;
import example.domain.model.contract.WageCondition;
import example.domain.type.time.HourAndMinute;

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

    Wage(HourAndMinute totalWorkTime, HourlyWage hourlyWage) {
        this(WorkHours.of(totalWorkTime).multiply(hourlyWage));
    }

    Wage(OverWorkTime overWorkTime, OverTimeHourlyExtraWage overTimeHourlyExtraWage) {
        this(WorkHours.of(overWorkTime).multiply(overTimeHourlyExtraWage.value()));
    }

    Wage(MidnightWorkTime midnightWorkTime, MidnightHourlyExtraWage midnightHourlyExtraWage) {
        this(WorkHours.of(midnightWorkTime).multiply(midnightHourlyExtraWage.value()));
    }

    public static Wage from(WorkTime workTime, WageCondition wageCondition) {
        return new Wage(workTime.totalWorkTime(), wageCondition.baseHourlyWage())
                .add(new Wage(workTime.overWorkTime(), wageCondition.overTimeHourlyExtraWage()))
                .add(new Wage(workTime.midnightWorkTime(), wageCondition.midnightHourlyExtraWage()));
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
