package example.domain.model.attendance;

import example.domain.type.time.HourTime;

/**
 * 業務開始時刻
 */
public class WorkStartTime {

    HourTime value;

    public WorkStartTime(HourTime value) {
        this.value = value;
    }

    HourTime normalizedHourTime() {
        int normalMinute = value.value().getMinute() / 15 * 15;
        return new HourTime(value.value().withMinute(normalMinute));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
