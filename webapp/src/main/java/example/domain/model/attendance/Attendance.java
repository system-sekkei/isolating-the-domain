package example.domain.model.attendance;

import example.domain.model.timerecord.evaluation.*;
import example.domain.type.time.Hour;
import example.domain.type.time.QuarterHour;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 勤怠
 */
public class Attendance {

    WorkMonth month;
    TimeRecords timeRecords;
    TimeRecords beforeMonthTimeRecords;

    public Attendance(WorkMonth month, TimeRecords timeRecords, TimeRecords beforeMonthTimeRecords) {
        this.month = month;
        this.timeRecords = timeRecords;
        this.beforeMonthTimeRecords = beforeMonthTimeRecords;
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

    public TimeRecords timeRecords() {
        return timeRecords;
    }

    TimeRecords timeRecordsWithBeforeMonth() {
        List<TimeRecord> list = Stream.concat(
            beforeMonthTimeRecords.list().stream(),
            timeRecords.list().stream()).collect(Collectors.toList());
        return new TimeRecords(list);
    }

    public OverLegalMoreThan60HoursWorkTime overLegalMoreThan60HoursWorkTime() {
        OverLegalHoursWorkTime overLegalHoursWorkTime = overLegalHoursWorkTimes();
        return new OverLegalMoreThan60HoursWorkTime(overLegalHoursWorkTime.quarterHour().overMinute(new QuarterHour(new Hour(60))));
    }

    public OverLegalWithin60HoursWorkTime overLegalWithin60HoursWorkTime() {
        OverLegalHoursWorkTime overLegalHoursWorkTime = overLegalHoursWorkTimes();

        if (overLegalHoursWorkTime.monthlyOverLegalHoursStatus() == MonthlyOverLegalHoursStatus.月６０時間超) {
            return new OverLegalWithin60HoursWorkTime(new QuarterHour(new Hour(60)));
        }

        return new OverLegalWithin60HoursWorkTime(overLegalHoursWorkTime.quarterHour());
    }

    public OverLegalHoursWorkTime overLegalHoursWorkTimes() {
        OverLegalHoursWorkTime total = new OverLegalHoursWorkTime(new QuarterHour());
        for (TimeRecord timeRecord : timeRecords.list()) {
            OverLegalHoursWorkTime overLegalHoursWorkTime = timeRecord.actualWorkDateTime().overLegalHoursWorkTime(this);
            total = total.add(overLegalHoursWorkTime);
        }

        return total;
    }

    public WeeklyTimeRecord weeklyRecords(WorkDate workDate) {
        // TODO: WeeklyTimeRecordにファクトリーメソッドつくる
        return new WeeklyTimeRecord(new TimeRecords(timeRecordsWithBeforeMonth().list().stream().filter(record -> record.workDate().sameWeek(workDate)).collect(toList())));
    }
}