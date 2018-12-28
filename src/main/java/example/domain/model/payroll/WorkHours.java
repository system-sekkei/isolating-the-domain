package example.domain.model.payroll;

import example.domain.model.contract.HourlyWage;
import example.domain.type.time.Minute;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 労働時間（小数点含む）
 */
public class WorkHours {
    BigDecimal value;

    WorkHours(Minute minute) {
        // TODO quarterRoundDownしているMinuteであることを型にしたい
        //XXX 15分刻みなので２桁で十分
        this.value = new BigDecimal(minute.value()).divide(new BigDecimal(60), 2, RoundingMode.DOWN);
    }

    public static WorkHours of(Minute minute) {
        return new WorkHours(minute);
    }

    public BigDecimal value() {
        return value;
    }

    public BigDecimal multiply(HourlyWage hourlyWage) {
        return this.value.multiply(BigDecimal.valueOf(hourlyWage.value()));
    }
}
