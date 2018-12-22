package example.domain.model.attendance;

import example.domain.type.time.ClockTime;

/**
 * 業務終了時刻
 */
public class WorkEndTime {

    ClockTime value;

    @Deprecated
    WorkEndTime() {
    }

    public WorkEndTime(ClockTime value) {
        this.value = value;
    }

    ClockTime normalizedHourTime() {
        return value.quarterRoundDown();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
