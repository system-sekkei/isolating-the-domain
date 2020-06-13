package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.WeeklyWorkingHoursLimit;
import example.domain.model.legislation.WeeklyWorkingHoursStatus;
import example.domain.type.time.QuarterHour;

/**
 * 法定時間外労働 労働時間
 */
public class OverLegalHoursWorkTime {
    OverLegalWithin60HoursWorkTime within60Hours;
    OverLegalMoreThan60HoursWorkTime moreThan60Hours;

    public OverLegalHoursWorkTime(OverLegalWithin60HoursWorkTime within60Hours, OverLegalMoreThan60HoursWorkTime moreThan60Hours) {
        this.within60Hours = within60Hours;
        this.moreThan60Hours = moreThan60Hours;
    }

    public static OverLegalHoursWorkTime from(MonthlyTimeRecord monthlyTimeRecord, BeforeMonthlyTimeRecord beforeMonthlyTimeRecord, WorkDate workDate) {
        WeeklyTimeRecord 週の勤務実績 = WeeklyTimeRecord.from(monthlyTimeRecord, beforeMonthlyTimeRecord, workDate);
        ActualWorkDateTime 勤務日時実績 = monthlyTimeRecord.value.at(workDate).actualWorkDateTime;

        QuarterHour overLegalHoursWorkTime = new QuarterHour();
        if (週の勤務実績.weeklyWorkingHoursStatus(workDate) == WeeklyWorkingHoursStatus.法定時間内労働時間の累計が４０時間を超えている) {
            overLegalHoursWorkTime = overWeeklyLimitWorkTime(monthlyTimeRecord, beforeMonthlyTimeRecord, workDate, 週の勤務実績);
        } else if (勤務日時実績.workTime().dailyWorkingHoursStatus() == DailyWorkingHoursStatus.一日８時間を超えている) {
            overLegalHoursWorkTime = 勤務日時実績.overDailyLimitWorkTime();
        }

        // TODO: 月60時間超えの判定
        OverLegalWithin60HoursWorkTime within60Hours = new OverLegalWithin60HoursWorkTime(overLegalHoursWorkTime);
        OverLegalMoreThan60HoursWorkTime moreThan60Hours = new OverLegalMoreThan60HoursWorkTime(new QuarterHour());

        return new OverLegalHoursWorkTime(within60Hours, moreThan60Hours);
    }

    public static QuarterHour overWeeklyLimitWorkTime(MonthlyTimeRecord monthlyTimeRecord, BeforeMonthlyTimeRecord beforeMonthlyTimeRecord, WorkDate workDate, WeeklyTimeRecord weeklyToWorkDate) {
        TimeRecords 前日までの勤務実績 = weeklyToWorkDate.value.recordsDayBefore(workDate);
        QuarterHour overWorkTimeDayBefore = new QuarterHour();
        for (TimeRecord record : 前日までの勤務実績.list()) {
            overWorkTimeDayBefore = overWorkTimeDayBefore.add(from(monthlyTimeRecord, beforeMonthlyTimeRecord, record.workDate()).quarterHour());
        }

        QuarterHour overLegalHoursWorkTime = weeklyToWorkDate.workTimes().total().overMinute(WeeklyWorkingHoursLimit.legal().toMinute()).overMinute(overWorkTimeDayBefore);
        return overLegalHoursWorkTime;
    }

    public QuarterHour quarterHour() {
        return within60Hours.quarterHour().add(moreThan60Hours.quarterHour());
    }

    public OverLegalWithin60HoursWorkTime within60HoursWorkTime() {
        return within60Hours;
    }

    public OverLegalMoreThan60HoursWorkTime moreThan60HoursWorkTime() {
        return moreThan60Hours;
    }
}
