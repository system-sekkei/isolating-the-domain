package example.domain.model.legislation;

import example.domain.type.time.Hour;
import example.domain.type.time.Minute;

/**
 * 1日の労働時間上限
 */
public class DailyWorkingHoursLimit {
    Hour value;

    DailyWorkingHoursLimit(Hour value) {
        this.value = value;
    }

    public static DailyWorkingHoursLimit legal() {
        // 労働基準法第32条
        return new DailyWorkingHoursLimit(new Hour(8));
    }

    public Minute toMinute() {
        return value.toMinute();
    }
}
