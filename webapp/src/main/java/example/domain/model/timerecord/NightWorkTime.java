package example.domain.model.timerecord;

import example.domain.model.timerecord.breaktime.NightBreakTime;
import example.domain.type.time.QuarterHour;

/**
 * 深夜勤務時間
 */
public class NightWorkTime {

    QuarterHour value;

    public NightWorkTime(TimeRange timeRange, NightBreakTime nightBreakTime) {
        this.value = nightBreakTime.subtractFrom(timeRange.nightBindingTime());
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
