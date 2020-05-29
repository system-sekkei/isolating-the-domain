package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.WeeklyWorkingHoursLimit;
import example.domain.model.legislation.WeeklyWorkingHoursStatus;
import example.domain.type.time.Hour;
import example.domain.type.time.QuarterHour;

/**
 * 法定時間外労働 労働時間
 */
public class OverLegalHoursWorkTime {
    QuarterHour value;

    public OverLegalHoursWorkTime(QuarterHour value) {
        this.value = value;
    }

    public static OverLegalHoursWorkTime daily(ActualWorkDateTime actualWorkDateTime, WeeklyTimeRecord weeklyTimeRecord) {
        QuarterHour overLegalHoursWorkTime = new QuarterHour();
        if (weeklyTimeRecord.weeklyWorkingHoursStatus() == WeeklyWorkingHoursStatus.週の法定時間内労働時間の累計が４０時間を超えている) {
            WorkTimes weeklyWorkTimes = weeklyTimeRecord.value.workTimes();

            TimeRecords recordsDayBefore = weeklyTimeRecord.value.recordsDayBefore(actualWorkDateTime.workDate());
            QuarterHour overWorkTimeDayBefore = new QuarterHour();
            for (TimeRecord record : recordsDayBefore.list()) {
                overWorkTimeDayBefore = overWorkTimeDayBefore.add(daily(record.actualWorkDateTime, new WeeklyTimeRecord(recordsDayBefore)).quarterHour());
            }

            overLegalHoursWorkTime = weeklyWorkTimes.total().overMinute(WeeklyWorkingHoursLimit.legal().toMinute()).overMinute(overWorkTimeDayBefore);
        } else if (actualWorkDateTime.workTime().dailyWorkingHoursStatus() == DailyWorkingHoursStatus.一日８時間を超えている) {
            overLegalHoursWorkTime = actualWorkDateTime.workTime().overDailyLimitWorkTime();
        }

        return new OverLegalHoursWorkTime(overLegalHoursWorkTime);
    }

    OverLegalHoursWorkTime add(OverLegalHoursWorkTime value) {
        return new OverLegalHoursWorkTime(this.value.add(value.value));
    }

    static OverLegalHoursWorkTime max(OverLegalHoursWorkTime a, OverLegalHoursWorkTime b) {
        if (a.value.moreThan(b.value)) {
            return a;
        }
        return b;
     }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public MonthlyOverLegalHoursStatus monthlyOverLegalHoursStatus() {
        if (value.moreThan(new Hour(60))) {
            return MonthlyOverLegalHoursStatus.月６０時間超;
        }

        return MonthlyOverLegalHoursStatus.月６０時間以内;
    }
}
