package example.domain.model.attendance.worktimerecord;

import example.domain.model.labour_standards_law.Midnight;
import example.domain.type.time.ClockTimeRange;
import example.domain.type.time.Minute;

/**
 * 深夜拘束時間
 */
public class MidnightBindingTime {

    Minute value;

    public MidnightBindingTime(Minute value) {
        this.value = value;
    }

    public MidnightBindingTime(ClockTimeRange clockTimeRange) {
        this(clockTimeRange, Midnight.legal());
    }

    public MidnightBindingTime(ClockTimeRange clockTimeRange, Midnight midnight) {
        this(midnight.midnightMinute(clockTimeRange));
    }

    public Minute minute() {
        return value;
    }
}
