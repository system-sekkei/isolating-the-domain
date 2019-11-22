package example.domain.model.timerecord.evaluation;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 休憩時間合計
 */
public class BreakTime {

    Minute value;

    public BreakTime(DaytimeBreakTime daytimeBreakTime, NightBreakTime nightBreakTime) {
        value = daytimeBreakTime.minute().add(nightBreakTime.minute());
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }
}
