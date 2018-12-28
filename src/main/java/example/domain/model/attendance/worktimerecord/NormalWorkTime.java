package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.Minute;

public class NormalWorkTime {

    Minute value;

    public NormalWorkTime(WorkTimeRange workTimeRange, NormalBreakTime normalBreakTime) {
        value = normalBreakTime.subtractFrom(workTimeRange.normalBindingTime());
    }

    public Minute minute() {
        return value;
    }
}
