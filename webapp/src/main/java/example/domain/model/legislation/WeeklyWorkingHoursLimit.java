package example.domain.model.legislation;

import example.domain.type.time.Hour;

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
}
