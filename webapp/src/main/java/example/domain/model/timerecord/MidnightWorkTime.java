package example.domain.model.timerecord;

import example.domain.model.timerecord.breaktime.MidnightBreakTime;
import example.domain.type.time.QuarterHour;

/**
 * 深夜勤務時間
 */
public class MidnightWorkTime {

    QuarterHour value;

    public MidnightWorkTime(TimeRange timeRange, MidnightBreakTime midnightBreakTime) {
        this.value = midnightBreakTime.subtractFrom(timeRange.midnightBindingTime());
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
