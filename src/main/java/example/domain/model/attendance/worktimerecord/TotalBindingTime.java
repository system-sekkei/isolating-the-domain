package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 拘束時間
 */
public class TotalBindingTime {

    Minute value;

    public TotalBindingTime(Minute value) {
        this.value = value;
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }
}
