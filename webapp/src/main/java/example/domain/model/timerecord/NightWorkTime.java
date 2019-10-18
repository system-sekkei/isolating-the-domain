package example.domain.model.timerecord;

import example.domain.model.timerecord.breaktime.NightBreakTime;
import example.domain.type.time.QuarterHour;

/**
 * 深夜勤務時間
 */
public class NightWorkTime {

    QuarterHour value;

    public NightWorkTime(WorkRange workRange, NightBreakTime nightBreakTime) {
        this.value = nightBreakTime.subtractFrom(workRange.nightBindingTime());
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
