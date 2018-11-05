package example.domain.model.attendance;

import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;

public class WorkTime {

    HourTime value;

    public WorkTime(HourTime value) {
        this.value = value;
    }

    HourTime normalizedHourTime() {
        int normalMinute = value.value().getMinute() / 15 * 15;
        return new HourTime(value.value().withMinute(normalMinute));
    }

    public Minute until(WorkTime otherTime) {
        return this.normalizedHourTime().until(otherTime.normalizedHourTime());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
