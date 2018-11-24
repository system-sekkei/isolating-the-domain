package example.domain.model.attendance;

import example.domain.type.time.ClockTime;

/**
 * 業務開始時刻
 */
public class WorkStartTime {

    ClockTime value;

    public WorkStartTime(ClockTime value) {
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
