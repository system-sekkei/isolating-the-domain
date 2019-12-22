package example.domain.model.attendance;

import example.domain.model.legislation.DaysOff;
import example.domain.model.timerecord.evaluation.StatutoryDaysOffWork;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.timerecord.evaluation.WorkDate;
import example.domain.type.date.Week;
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

    WeekWorkTime weekWorkTime(Week week) {
        return new WeekWorkTime(timeRecords.list().stream()
                .filter(timeRecord -> week.contains(timeRecord.actualWorkDateTime().workDate().value()))
                .map(timeRecord -> timeRecord.actualWorkDateTime().workTime().quarterHour())
                .reduce(QuarterHour::add)
                .orElseGet(QuarterHour::new));
    }

    // TODO: 法定時間内残業 (所定労働時間を超えるが、法定時間内におさまる残業)
    // TODO: 法定時間外残業

    StatutoryDaysOffWork statutoryDaysOffWorkByWeek(Week week) {
        DaysOff daysOff = DaysOff.from(week);
        return new StatutoryDaysOffWork(timeRecords.list().stream()
            .filter(timeRecord -> timeRecord.actualWorkDateTime().workDate().value().hasSameValue(daysOff.value()))
            .map(timeRecord -> timeRecord.actualWorkDateTime().workTime().quarterHour())
            .reduce(QuarterHour::add)
            .orElseGet(QuarterHour::new));
    }

}