package example.domain.model.timerecord.breaktime;

import example.domain.model.timerecord.bindingtime.DaytimeBindingTime;
import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;

/**
 * 日中休憩時間
 */
public class DaytimeBreakTime {
    Minute value;

    @Deprecated
    DaytimeBreakTime() {
    }

    public DaytimeBreakTime(Minute value) {
        this.value = value;
    }

    public QuarterHour subtractFrom(DaytimeBindingTime daytimeBindingTime) {
        return daytimeBindingTime.quarterHour().subtract(value.quarterHourRoundUp());
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
