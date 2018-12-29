package example.domain.model.worktimerecord;

/**
 * 勤務時間実績
 */
public class WorkTimeRecord {
    WorkTimeRange workTimeRange;
    DaytimeBreakTime daytimeBreakTime;
    MidnightBreakTime midnightBreakTime;

    @Deprecated
    public WorkTimeRecord() {
    }

    public WorkTimeRecord(WorkTimeRange workTimeRange, DaytimeBreakTime daytimeBreakTime, MidnightBreakTime midnightBreakTime) {
        this.workTimeRange = workTimeRange;
        this.daytimeBreakTime = daytimeBreakTime;
        this.midnightBreakTime = midnightBreakTime;
    }

    public WorkTimeRange workTimeRange() {
        return workTimeRange;
    }

    public DaytimeBreakTime daytimeBreakTime() {
        return daytimeBreakTime;
    }

    public MidnightBreakTime midnightBreakTime() {
        return midnightBreakTime;
    }

    public WorkTime workTime() {
        return new WorkTime(daytimeWorkTime(), midnightWorkTime());
    }

    public BreakTime breakTime() {
        return new BreakTime(daytimeBreakTime, midnightBreakTime);
    }

    public DaytimeWorkTime daytimeWorkTime() {
        return new DaytimeWorkTime(workTimeRange, daytimeBreakTime);
    }

    public MidnightWorkTime midnightWorkTime() {
        return new MidnightWorkTime(workTimeRange, midnightBreakTime);
    }

    public OverWorkTime overWorkTime() {
        return new OverWorkTime(workTime());
    }
}
