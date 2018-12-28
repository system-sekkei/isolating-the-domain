package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 業務時間
 */
public class WorkTime {

    Minute value;

    public WorkTime(DaytimeWorkTime daytimeWorkTime, MidnightWorkTime midnightWorkTime) {
        this.value = daytimeWorkTime.minute().add(midnightWorkTime.minute());
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }
}
