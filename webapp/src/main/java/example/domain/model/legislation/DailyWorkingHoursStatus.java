package example.domain.model.legislation;

import example.domain.model.timerecord.evaluation.WorkTime;

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
