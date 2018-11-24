package example.domain.model.attendance;

import example.domain.model.labour_standards_law.DailyOvertimeWork;
import example.domain.model.labour_standards_law.Midnight;
import example.domain.type.time.ClockTimeRange;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 業務時刻の範囲
 */
public class WorkTimeRange {

    private final WorkStartTime startTime;
    private final WorkEndTime endTime;

    public WorkTimeRange(WorkStartTime startTime, WorkEndTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public WorkStartTime start() {
        return startTime;
    }

    public WorkEndTime end() {
        return endTime;
    }

    public ClockTimeRange toTimeRange() {
        return new ClockTimeRange(startTime.value, endTime.value);
    }

    public static WorkTimeRange of(ClockTimeRange timeRange) {
        return new WorkTimeRange(new WorkStartTime(timeRange.begin()), new WorkEndTime(timeRange.end()));
    }

    public HourAndMinute workTime() {
        return HourAndMinute.from(startTime.normalizedHourTime().until(endTime.normalizedHourTime()));
    }

    public HourAndMinute midnightWorkTime() {
        WorkTimeRange midnightRange = WorkTimeRange.of(toTimeRange().intersect(new Midnight().range()));
        return midnightRange.workTime();
    }

    public HourAndMinute overWorkTime() {
        Minute workMinute = workTime().toMinute();
        DailyOvertimeWork dailyOvertimeWork = DailyOvertimeWork.legal();
        Minute overMinute = dailyOvertimeWork.overMinute(workMinute);
        return HourAndMinute.from(overMinute);
    }
}
