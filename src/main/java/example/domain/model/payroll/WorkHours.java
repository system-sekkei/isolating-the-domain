package example.domain.model.payroll;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 労働時間（小数点含む）
 */
public class WorkHours {
    BigDecimal value;

    WorkHours(Minute minute) {
        //XXX 15分刻みなので２桁で十分
        value = new BigDecimal(minute.value()).divide(new BigDecimal(60), 2, RoundingMode.DOWN);
        int value = minute.value();
    }

    public static WorkHours of(Minute minute) {
        return new WorkHours(minute);
    }

    public static WorkHours of(HourAndMinute hourAndMinute) {
        return new WorkHours(hourAndMinute.toMinute());
    }

    public BigDecimal value() {
        return value;
    }
}
