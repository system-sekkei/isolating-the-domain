package example.domain.model.attendance;

import example.domain.model.attendance.worktimerecord.MidnightWorkTime;
import example.domain.model.attendance.worktimerecord.OverWorkTime;
import example.domain.model.attendance.worktimerecord.TotalWorkTime;
import example.domain.type.time.Minute;

/**
 * 労働時間
 */
public class WorkTimeSummary {

    Minute normalTime;
    Minute midnightWorkTime;
    Minute overWorkTime;

    public WorkTimeSummary() {
        this(new Minute(0), new Minute(0), new Minute(0));
    }

    public WorkTimeSummary(Minute normalTime, Minute midnightWorkTime, Minute overWorkTime) {
        this.normalTime = normalTime;
        this.midnightWorkTime = midnightWorkTime;
        this.overWorkTime = overWorkTime;
    }

    public WorkTimeSummary(Attendance attendance) {
        this(attendance.workTimeRecord().normalWorkTime().minute(), attendance.workTimeRecord().midnightWorkTime().minute(), attendance.workTimeRecord().overTime().minute());
    }

    public WorkTimeSummary addAttendanceOfDay(Attendance attendance) {
        return add(new WorkTimeSummary(attendance));
    }

    public WorkTimeSummary add(WorkTimeSummary other) {
        return new WorkTimeSummary(
                this.normalTime.add(other.normalTime),
                this.midnightWorkTime.add(other.midnightWorkTime),
                this.overWorkTime.add(other.overWorkTime));
    }

    public TotalWorkTime totalWorkTime() {
        return new TotalWorkTime(normalTime.add(midnightWorkTime));
    }

    public MidnightWorkTime midnightWorkTime() {
        return new MidnightWorkTime(midnightWorkTime);
    }

    public OverWorkTime overWorkTime() {
        return new OverWorkTime(overWorkTime);
    }

}