package example.domain.model.payroll;

import java.math.BigDecimal;

import example.domain.model.attendance.WorkHours;
import example.domain.model.contract.HourlyWage;
import example.domain.type.time.HourAndMinute;

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

    public static Wage of(HourAndMinute workTime, HourlyWage hourlyWage) {
        BigDecimal tmp = new BigDecimal(workTime.toMinute().value() * hourlyWage.value());
        //XXX 中間点計算なので小数点以下はならべく保持してみる
        BigDecimal value = tmp.divide(new BigDecimal(60), 100, BigDecimal.ROUND_DOWN);
        System.out.println(value);
        return new Wage(value);
    }

    public Wage add(Wage other) {
        return new Wage(value.add(other.value));
    }
}
