package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.DailyWorkingHoursLimit;

/**
 * 1日の労働時間の状態
 */
public enum DailyWorkingHoursStatus {
    一日８時間以下,
    一日８時間を超えている;

    public static DailyWorkingHoursStatus from(WorkTime workTime) {
        if (workTime.quarterHour().moreThan(DailyWorkingHoursLimit.legal().toMinute())) {
            return DailyWorkingHoursStatus.一日８時間を超えている;
        }
        return DailyWorkingHoursStatus.一日８時間以下;
    }
}
