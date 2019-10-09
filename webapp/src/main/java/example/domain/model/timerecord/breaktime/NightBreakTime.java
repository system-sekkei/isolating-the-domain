package example.domain.model.timerecord.breaktime;

import example.domain.model.timerecord.bindingtime.NightBindingTime;
import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;

/**
 * 休憩時間（深夜）
 */
public class NightBreakTime {
    Minute value;

    @Deprecated
    NightBreakTime() {
    }

    public NightBreakTime(Minute value) {
        this.value = value;
    }

    public QuarterHour subtractFrom(NightBindingTime nightBindingTime) {
        return nightBindingTime.quarterHour().subtract(value.quarterHourRoundUp());
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
