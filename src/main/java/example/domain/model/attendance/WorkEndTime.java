package example.domain.model.attendance;

import example.domain.type.time.HourTime;

/**
 * 業務終了時刻
 */
public class WorkEndTime {

    HourTime value;

    public WorkEndTime(HourTime value) {
        this.value = value;
    }

    HourTime normalizedHourTime() {
        return value.normalizedQuarterRoundDown();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
