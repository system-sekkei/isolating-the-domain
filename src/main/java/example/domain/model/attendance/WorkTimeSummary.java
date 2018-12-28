package example.domain.model.attendance;

import example.domain.type.time.Minute;

import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 労働時間
 */
public class WorkTimeSummary {

    Minute normalTime;
    Minute midnightWorkTime;
    Minute overWorkTime;

    private WorkTimeSummary() {
        this(new Minute(0), new Minute(0), new Minute(0));
    }

    private WorkTimeSummary(Attendance attendance) {
        this(attendance.workTimeRecord().normalWorkTime().minute(), attendance.workTimeRecord().midnightWorkTime().minute(), attendance.workTimeRecord().overTime().minute());
    }

    private WorkTimeSummary(Minute normalTime, Minute midnightWorkTime, Minute overWorkTime) {
        this.normalTime = normalTime;
        this.midnightWorkTime = midnightWorkTime;
        this.overWorkTime = overWorkTime;
    }

    public static Collector<Attendance, ?, WorkTimeSummary> collector() {
        return Collectors.reducing(new WorkTimeSummary(),
                WorkTimeSummary::new,
                WorkTimeSummary::add);
    }

    public WorkTimeSummary add(WorkTimeSummary other) {
        return new WorkTimeSummary(
                this.normalTime.add(other.normalTime),
                this.midnightWorkTime.add(other.midnightWorkTime),
                this.overWorkTime.add(other.overWorkTime));
    }

    public AllWorkTime totalWorkTime() {
        return new AllWorkTime(normalTime.add(midnightWorkTime));
    }

    public AllMidnightWorkTime midnightWorkTime() {
        return new AllMidnightWorkTime(midnightWorkTime);
    }

    public AllOverWorkTime overWorkTime() {
        return new AllOverWorkTime(overWorkTime);
    }
}
