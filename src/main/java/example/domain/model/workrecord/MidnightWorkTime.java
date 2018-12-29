package example.domain.model.workrecord;

import example.domain.type.time.QuarterHour;

/**
 * 深夜勤務時間
 */
public class MidnightWorkTime {

    QuarterHour value;

    public MidnightWorkTime(WorkTimeRange workTimeRange, MidnightBreakTime midnightBreakTime) {
        this.value = midnightBreakTime.subtractFrom(workTimeRange.midnightBindingTime());
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
