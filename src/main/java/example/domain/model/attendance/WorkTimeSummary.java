package example.domain.model.attendance;

import example.domain.type.time.Minute;

import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 労働時間
 */
public class WorkTimeSummary {

    Minute daytimeWorkTime;
    Minute midnightWorkTime;
    Minute overWorkTime;

    WorkTimeSummary() {
        this(new Minute(0), new Minute(0), new Minute(0));
    }

    WorkTimeSummary(Attendance attendance) {
        this(attendance.workTimeRecord().daytimeWorkTime().minute(), attendance.workTimeRecord().midnightWorkTime().minute(), attendance.workTimeRecord().overWorkTime().minute());
    }

    WorkTimeSummary(Minute daytimeWorkTime, Minute midnightWorkTime, Minute overWorkTime) {
        this.daytimeWorkTime = daytimeWorkTime;
        this.midnightWorkTime = midnightWorkTime;
        this.overWorkTime = overWorkTime;
    }

    WorkTimeSummary add(WorkTimeSummary other) {
        return new WorkTimeSummary(
                this.daytimeWorkTime.add(other.daytimeWorkTime),
                this.midnightWorkTime.add(other.midnightWorkTime),
                this.overWorkTime.add(other.overWorkTime));
    }

    public TotalWorkTime totalWorkTime() {
        return new TotalWorkTime(daytimeWorkTime.add(midnightWorkTime));
    }

    public TotalMidnightWorkTime totalMidnightWorkTime() {
        return new TotalMidnightWorkTime(midnightWorkTime);
    }

    public TotalOverWorkTime totalOverWorkTime() {
        return new TotalOverWorkTime(overWorkTime);
    }

    public static Collector<Attendance, ?, WorkTimeSummary> collector() {
        return Collectors.reducing(new WorkTimeSummary(),
                WorkTimeSummary::new,
                WorkTimeSummary::add);
    }
}
