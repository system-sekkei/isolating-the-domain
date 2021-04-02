package example.domain.model.legislation;

import example.domain.type.time.Hour;
import example.domain.type.time.Minute;

/**
 * 1週間の労働時間上限
 */
public class WeeklyWorkingHoursLimit {
    Hour value;

    WeeklyWorkingHoursLimit(Hour value) {
        this.value = value;
    }

    public static WeeklyWorkingHoursLimit legal() {
        // 労働基準法第32条
        return new WeeklyWorkingHoursLimit(new Hour(40));
    }

    public Minute toMinute() {
        return value.toMinute();
    }
}
