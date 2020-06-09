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

    public static OverLegalHoursWorkTime daily(ActualWorkDateTime actualWorkDateTime, WeeklyTimeRecord weeklyTimeRecord) {
        QuarterHour overLegalHoursWorkTime = weeklyCalc(actualWorkDateTime, weeklyTimeRecord); // TODO: Dailyの中にかかない

        if (actualWorkDateTime.workTime().dailyWorkingHoursStatus() == DailyWorkingHoursStatus.一日８時間を超えている) {
            overLegalHoursWorkTime = actualWorkDateTime.overDailyLimitWorkTime();
        }

        // TODO: 月60時間超えの判定
        OverLegalWithin60HoursWorkTime within60Hours = new OverLegalWithin60HoursWorkTime(overLegalHoursWorkTime);
        OverLegalMoreThan60HoursWorkTime moreThan60Hours = new OverLegalMoreThan60HoursWorkTime(new QuarterHour());

        return new OverLegalHoursWorkTime(within60Hours, moreThan60Hours);
    }

    public static QuarterHour weeklyCalc(ActualWorkDateTime actualWorkDateTime, WeeklyTimeRecord weeklyTimeRecord) {
        WeeklyTimeRecord weeklyToWorkDate = weeklyTimeRecord.recordsToDate(actualWorkDateTime.workDate());
        QuarterHour overLegalHoursWorkTime = new QuarterHour();
        if (weeklyToWorkDate.weeklyWorkingHoursStatus() == WeeklyWorkingHoursStatus.法定時間内労働時間の累計が４０時間を超えている) {
            overLegalHoursWorkTime = weekly(actualWorkDateTime, weeklyToWorkDate);
        }
        return overLegalHoursWorkTime;
    }

    public static QuarterHour weekly(ActualWorkDateTime actualWorkDateTime, WeeklyTimeRecord weeklyToWorkDate) {
        QuarterHour overLegalHoursWorkTime;
        TimeRecords recordsDayBefore = weeklyToWorkDate.value.recordsDayBefore(actualWorkDateTime.workDate());
        QuarterHour overWorkTimeDayBefore = new QuarterHour();
        for (TimeRecord record : recordsDayBefore.list()) {
            overWorkTimeDayBefore = overWorkTimeDayBefore.add(daily(record.actualWorkDateTime, new WeeklyTimeRecord(recordsDayBefore)).quarterHour());
        }

        overLegalHoursWorkTime = weeklyToWorkDate.workTimes().total().overMinute(WeeklyWorkingHoursLimit.legal().toMinute()).overMinute(overWorkTimeDayBefore);
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
