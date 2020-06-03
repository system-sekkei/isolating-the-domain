package example.domain.model.attendance;

import example.domain.model.timerecord.evaluation.*;
import example.domain.type.time.Hour;
import example.domain.type.time.QuarterHour;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 勤怠
 */
public class Attendance {

    WorkMonth month;
    WeeklyTimeRecords weeklyTimeRecords;

    public Attendance(WorkMonth month, WeeklyTimeRecords weeklyTimeRecords) {
        this.month = month;
        this.weeklyTimeRecords = weeklyTimeRecords;
    }

    public WorkMonth month() {
        return month;
    }

    public List<WorkDate> listWorkDates() {
        return month.days();
    }

    public TimeRecord at(WorkDate workDate) {
        return monthlyTimeRecords().at(workDate);
    }

    public AttendanceStatus statusOf(WorkDate workDate) {
        return AttendanceStatus.from(monthlyTimeRecords().recordedAt(workDate));
    }

    public AttendDates attendDates() {
        return monthlyTimeRecords().attendDates();
    }

    public TotalWorkTime totalWorkTime() {
        return new TotalWorkTime(monthlyTimeRecords().list().stream()
                .map(timeRecord -> timeRecord.actualWorkDateTime().workTime().quarterHour())
                .reduce(QuarterHour::add)
                .orElseGet(QuarterHour::new));
    }

    public List<PayableWork> listPayableWork() {
        return monthlyTimeRecords().list().stream()
                .map(timeRecord -> new PayableWork(timeRecord.actualWorkDateTime()))
                .collect(Collectors.toList());
    }

    public TimeRecords monthlyTimeRecords() {
        return weeklyTimeRecords.monthlyTimeRecords(month);
    }

    public OverLegalMoreThan60HoursWorkTime overLegalMoreThan60HoursWorkTime() {
        OverLegalHoursWorkTime overLegalHoursWorkTime = monthlyTimeRecords().overLegalHoursWorkTimes();
        return new OverLegalMoreThan60HoursWorkTime(overLegalHoursWorkTime.quarterHour().overMinute(new QuarterHour(new Hour(60))));
    }

    public OverLegalWithin60HoursWorkTime overLegalWithin60HoursWorkTime() {
        OverLegalHoursWorkTime overLegalHoursWorkTime = monthlyTimeRecords().overLegalHoursWorkTimes();

        if (overLegalHoursWorkTime.monthlyOverLegalHoursStatus() == MonthlyOverLegalHoursStatus.月６０時間超) {
            return new OverLegalWithin60HoursWorkTime(new QuarterHour(new Hour(60)));
        }

        return new OverLegalWithin60HoursWorkTime(overLegalHoursWorkTime.quarterHour());
    }

}