package example.domain.model.timerecord.breaktime;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 休憩時間合計
 */
public class BreakTime {

    Minute value;

    public BreakTime(DaytimeBreakTime daytimeBreakTime, MidnightBreakTime midnightBreakTime) {
        value = daytimeBreakTime.minute().add(midnightBreakTime.minute());
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }
}
