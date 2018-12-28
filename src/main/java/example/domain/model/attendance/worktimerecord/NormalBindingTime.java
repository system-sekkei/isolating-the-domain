package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.Minute;

/**
 * 日中拘束時間
 */
public class NormalBindingTime {

    Minute value;

    public NormalBindingTime(TotalBindingTime totalBindingTime, MidnightBindingTime midnightBindingTime) {
        this.value = totalBindingTime.minute().subtract(midnightBindingTime.minute());
    }

    public Minute minute() {
        return value;
    }
}
