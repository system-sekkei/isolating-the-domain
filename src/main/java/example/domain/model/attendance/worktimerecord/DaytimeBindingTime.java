package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.Minute;

/**
 * 日中拘束時間
 */
public class DaytimeBindingTime {

    Minute value;

    public DaytimeBindingTime(BindingTime bindingTime, MidnightBindingTime midnightBindingTime) {
        this.value = bindingTime.minute().subtract(midnightBindingTime.minute());
    }

    public Minute minute() {
        return value;
    }
}
