package example.domain.model.attendance;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 総深夜労働時間
 */
public class TotalMidnightWorkTime {

    Minute value;

    public TotalMidnightWorkTime(Minute value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }

    public Minute minute() {
        return value;
    }
}
