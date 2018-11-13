package example.domain.model.attendance;

import example.domain.type.time.Minute;

import java.math.BigDecimal;

/**
 * 労働時間（小数点含む）
 */
public class WorkHours {
    BigDecimal value;

    WorkHours(Minute minute) {
        int value = minute.value();
    }

    public BigDecimal value() {
        return value;
    }
}
