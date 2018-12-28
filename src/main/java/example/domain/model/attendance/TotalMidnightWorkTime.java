package example.domain.model.attendance;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.QuarterHour;

/**
 * 総深夜労働時間
 */
public class TotalMidnightWorkTime {

    QuarterHour value;

    public TotalMidnightWorkTime(QuarterHour value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value.minute()).toString();
    }

    public QuarterHour quarterHour() {
        return value;
    }
}
