package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.DailyWorkingHoursStatus;
import example.domain.model.legislation.WeeklyWorkingHoursLimit;
import example.domain.model.legislation.WeeklyWorkingHoursStatus;
import example.domain.model.timerecord.timefact.WorkRange;
import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;
import example.domain.validation.BusinessLogic;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.util.stream.Collectors;

/**
 * 勤務日時実績
 */
public class ActualWorkDateTime {

    @Valid
    WorkRange workRange;
    DaytimeBreakTime daytimeBreakTime;
    NightBreakTime nightBreakTime;

    boolean daytimeBreakTimeValid;
    boolean nightBreakTimeValid;

    @Deprecated
    public ActualWorkDateTime() {
    }

    public ActualWorkDateTime(WorkRange workRange, DaytimeBreakTime daytimeBreakTime, NightBreakTime nightBreakTime) {
        this.workRange = workRange;
        this.daytimeBreakTime = daytimeBreakTime;
        this.nightBreakTime = nightBreakTime;
    }

    public WorkRange workRange() {
        return workRange;
    }

    public DaytimeBreakTime daytimeBreakTime() {
        return daytimeBreakTime;
    }

    public NightBreakTime nightBreakTime() {
        return nightBreakTime;
    }

    public WorkDate workDate() {
        return new WorkDate(workRange.start().date());
    }

    public WorkTime workTime() {
        return new WorkTime(daytimeWorkTime(), nightWorkTime());
    }

    public BreakTime breakTime() {
        return new BreakTime(daytimeBreakTime, nightBreakTime);
    }

    public DaytimeWorkTime daytimeWorkTime() {
        DaytimeBindingTime daytimeBindingTime = daytimeBindingTime();
        return daytimeBindingTime.subtract(daytimeBreakTime);
    }

    public DaytimeBindingTime daytimeBindingTime() {
        BindingTime bindingTime = new BindingTime(workRange);
        NightBindingTime nightBindingTime = nightBindingTime();
        return new DaytimeBindingTime(bindingTime, nightBindingTime);
    }

    public NightBindingTime nightBindingTime() {
        return new NightBindingTime(workRange);
    }

    public NightWorkTime nightWorkTime() {
        return nightBindingTime().subtract(nightBreakTime);
    }

    public LegalDaysOffWorkTime legalDaysOffWorkTime() {
        // TODO:
        return new LegalDaysOffWorkTime(new QuarterHour());
    }

    public OverLegalHoursWorkTime overLegalHoursWorkTime(TimeRecords timeRecords) {
        TimeRecords weeklyTimeRecord = timeRecords.weeklyRecords(workDate()).recordsToDate(workDate());
        WorkTimes weeklyWorkTimes = new WorkTimes(weeklyTimeRecord.list().stream()
                .map(timeRecord -> timeRecord.actualWorkDateTime.workTime()).collect(Collectors.toList()));

        WeeklyWorkingHoursStatus weeklyWorkingHoursStatus;
        if (weeklyWorkTimes.total().moreThan(new QuarterHour(WeeklyWorkingHoursLimit.legal().toMinute()))) {
            weeklyWorkingHoursStatus = WeeklyWorkingHoursStatus.週の累計労働時間が４０時間を超えている;
        } else {
            weeklyWorkingHoursStatus = WeeklyWorkingHoursStatus.週の累計労働時間が４０時間以内;
        }

        QuarterHour overLegalHoursWorkTime = new QuarterHour();
        if (weeklyWorkingHoursStatus == WeeklyWorkingHoursStatus.週の累計労働時間が４０時間を超えている) {
            // 週40超えの時間 - 週累計の法定超え残業
            QuarterHour weeklyOverLegalHoursWorkTime = weeklyWorkTimes.total().overMinute(new QuarterHour(WeeklyWorkingHoursLimit.legal().toMinute()));

            TimeRecords recordsDayBefore = weeklyTimeRecord.recordsDayBefore(workDate());
            OverLegalHoursWorkTime overWorkTimeDayBefore = new WorkTimes(recordsDayBefore.list().stream()
                    .map(timeRecord -> timeRecord.actualWorkDateTime.workTime()).collect(Collectors.toList())).dailyOverLegalHoursWorkTimeTotal();

            overLegalHoursWorkTime = weeklyOverLegalHoursWorkTime.subtract(overWorkTimeDayBefore.quarterHour());
        } else if (workTime().dailyWorkingHoursStatus() == DailyWorkingHoursStatus.一日８時間を超えている) {
            overLegalHoursWorkTime = workTime().dailyOverLegalHoursWorkTime();
        }

        return new OverLegalHoursWorkTime(overLegalHoursWorkTime);
    }

    public OverLegalMoreThan60HoursWorkTime overLegalMoreThan60HoursWorkTime() {
        // TODO:
        return new OverLegalMoreThan60HoursWorkTime(new QuarterHour());
    }

    public OverLegalWithin60HoursWorkTime overLegalWithin60HoursWorkTime(TimeRecords timeRecords) {
        // TODO: 月の超過時間集計を考慮した処理にする
        return new OverLegalWithin60HoursWorkTime(overLegalHoursWorkTime(timeRecords).quarterHour());
    }


    @AssertTrue(message = "休憩時間が不正です", groups = BusinessLogic.class)
    public boolean isDaytimeBreakTimeValid() {
        Minute daytimeBindingMinute = daytimeBindingTime().quarterHour().minute();
        if (daytimeBindingMinute.lessThan(daytimeBreakTime.minute())) {
            return false;
        }

        return true;
    }

    @AssertTrue(message = "休憩時間（深夜）が不正です", groups = BusinessLogic.class)
    public boolean isNightBreakTimeValid() {
        Minute nightBindingMinute = nightBindingTime().quarterHour().minute();
        if (nightBindingMinute.lessThan(nightBreakTime.minute())) {
            return false;
        }

        return true;
    }
}
