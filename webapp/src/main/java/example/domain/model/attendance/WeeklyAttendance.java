package example.domain.model.attendance;

import example.domain.type.date.Week;
import example.domain.type.time.QuarterHour;

/**
 * 週の勤怠
 */
public class WeeklyAttendance {
    Week week;
    TimeRecords timeRecords;

    public WeeklyAttendance(Week week, TimeRecords timeRecords) {
        this.week = week;
        this.timeRecords = timeRecords;
    }

    public WeekWorkTime weekWorkTime() {
        return new WeekWorkTime(timeRecords.list().stream()
                .map(timeRecord -> timeRecord.actualWorkDateTime().workTime().quarterHour())
                .reduce(QuarterHour::add)
                .orElseGet(QuarterHour::new));
    }
}