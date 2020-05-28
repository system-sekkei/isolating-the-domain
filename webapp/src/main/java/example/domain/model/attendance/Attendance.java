package example.domain.model.attendance;

import example.domain.model.legislation.DaysOff;
import example.domain.model.timerecord.evaluation.*;
import example.domain.type.date.Week;
import example.domain.type.time.Hour;
import example.domain.type.time.QuarterHour;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 勤怠
 */
public class Attendance {

    WorkMonth month;
    TimeRecords timeRecords;

    public Attendance(WorkMonth month, TimeRecords timeRecords) {
        this.month = month;
        this.timeRecords = timeRecords;
    }

    public WorkMonth month() {
        return month;
    }

    public List<WorkDate> listWorkDates() {
        return month.days();
    }

    public TimeRecord at(WorkDate workDate) {
        return timeRecords.at(workDate);
    }

    public AttendanceStatus statusOf(WorkDate workDate) {
        return AttendanceStatus.from(timeRecords.recordedAt(workDate));
    }

    public AttendDates attendDates() {
        return timeRecords.attendDates();
    }

    public TotalWorkTime totalWorkTime() {
        return new TotalWorkTime(timeRecords.list().stream()
                .map(timeRecord -> timeRecord.actualWorkDateTime().workTime().quarterHour())
                .reduce(QuarterHour::add)
                .orElseGet(QuarterHour::new));
    }

    public List<PayableWork> listPayableWork() {
        return timeRecords.list().stream()
                .map(timeRecord -> new PayableWork(timeRecord.actualWorkDateTime()))
                .collect(Collectors.toList());
    }

    public OverLegalMoreThan60HoursWorkTime overLegalMoreThan60HoursWorkTime() {
        OverLegalHoursWorkTime overLegalHoursWorkTime = timeRecords.overLegalHoursWorkTimes();
        return new OverLegalMoreThan60HoursWorkTime(overLegalHoursWorkTime.quarterHour().overMinute(new QuarterHour(new Hour(60))));
    }

    public OverLegalWithin60HoursWorkTime overLegalWithin60HoursWorkTime() {
        OverLegalHoursWorkTime overLegalHoursWorkTime = timeRecords.overLegalHoursWorkTimes();

        if (overLegalHoursWorkTime.monthlyOverLegalHoursStatus() == MonthlyOverLegalHoursStatus.月６０時間超) {
            return new OverLegalWithin60HoursWorkTime(new QuarterHour(new Hour(60)));
        }

        return new OverLegalWithin60HoursWorkTime(overLegalHoursWorkTime.quarterHour());
    }

    public LegalDaysOffWorkTime legalDaysOffWorkTime() {
        return timeRecords.legalDaysOffWorkTime();
    }
}