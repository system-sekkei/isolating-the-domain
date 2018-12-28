package example.domain.model.attendance.worktimerecord;

/**
 * 労働時間実績
 */
public class WorkTimeRecord {
    WorkTimeRange workTimeRange;
    NormalBreakTime normalBreakTime;
    MidnightBreakTime midnightBreakTime;

    @Deprecated
    public WorkTimeRecord() {
    }

    public WorkTimeRecord(WorkTimeRange workTimeRange, NormalBreakTime normalBreakTime, MidnightBreakTime midnightBreakTime) {
        this.workTimeRange = workTimeRange;
        this.normalBreakTime = normalBreakTime;
        this.midnightBreakTime = midnightBreakTime;
    }

    public WorkTimeRange workTimeRange() {
        return workTimeRange;
    }

    public NormalBreakTime normalBreakTime() {
        return normalBreakTime;
    }

    public MidnightBreakTime midnightBreakTime() {
        return midnightBreakTime;
    }

    public TotalWorkTime totalWorkTime() {
        return new TotalWorkTime(workTimeRange, totalBreakTime());
    }

    public TotalBreakTime totalBreakTime() {
        return new TotalBreakTime(normalBreakTime, midnightBreakTime);
    }

    public NormalWorkTime normalWorkTime() {
        return new NormalWorkTime(workTimeRange, normalBreakTime());
    }

    public MidnightWorkTime midnightWorkTime() {
        return new MidnightWorkTime(workTimeRange, midnightBreakTime());
    }

    public OverWorkTime overTime() {
        return new OverWorkTime(totalWorkTime());
    }
}
