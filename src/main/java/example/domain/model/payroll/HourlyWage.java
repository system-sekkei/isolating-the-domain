package example.domain.model.payroll;

import example.domain.model.attendance.WorkHours;
import example.domain.model.labour_standards_law.ExtraPayRate;

import java.math.BigDecimal;

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

    public Wage calculateWage(WorkHours workHours) {
        BigDecimal workHoursValue = workHours.value();
        // TODO まるめ
        return new Wage(BigDecimal.valueOf(value).multiply(workHoursValue));
    }

    public HourlyWage withExtraRate(ExtraPayRate extraRate) {
        // TODO 計算
        return this;
    }
}
