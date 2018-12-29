package example.domain.model.attendance;

import example.domain.model.workrecord.WorkRecord;
import example.domain.model.workrecord.WorkRecords;
import example.domain.type.time.QuarterHour;

import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 勤務時間集計
 */
public class WorkTimeSummary {

    QuarterHour daytimeWorkTime;
    QuarterHour midnightWorkTime;
    QuarterHour overWorkTime;

    WorkTimeSummary() {
        this(new QuarterHour(), new QuarterHour(), new QuarterHour());
    }

    WorkTimeSummary(WorkRecord workRecord) {
        this(workRecord.workTimeRecord().daytimeWorkTime().quarterHour(), workRecord.workTimeRecord().midnightWorkTime().quarterHour(), workRecord.workTimeRecord().overWorkTime().quarterHour());
    }

    WorkTimeSummary(QuarterHour daytimeWorkTime, QuarterHour midnightWorkTime, QuarterHour overWorkTime) {
        this.daytimeWorkTime = daytimeWorkTime;
        this.midnightWorkTime = midnightWorkTime;
        this.overWorkTime = overWorkTime;
    }

    public WorkTimeSummary(WorkRecords workRecords) {
        WorkTimeSummary temp = workRecords.list().stream().collect(WorkTimeSummary.collector());
        this.daytimeWorkTime = temp.daytimeWorkTime;
        this.midnightWorkTime = temp.midnightWorkTime;
        this.overWorkTime = temp.overWorkTime;
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

    public static Collector<WorkRecord, ?, WorkTimeSummary> collector() {
        return Collectors.reducing(new WorkTimeSummary(),
                WorkTimeSummary::new,
                WorkTimeSummary::add);
    }
}
