package example.domain.model.payroll;


import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;

import java.time.LocalTime;

public class TimeRecord {
    HourTime start;
    HourTime end;
    Minute breaks;

    public TimeRecord(HourTime start, HourTime end, Minute breaks) {
        this.start = start;
        this.end = end;
        this.breaks = breaks;
    }

    static HourTime normalize(HourTime org) {
        LocalTime lt = org.value();
        int normalMinute = (lt.getMinute() / 15 * 15);
        return new HourTime(LocalTime.of(lt.getHour(), normalMinute));
    }

    static Minute normalize(Minute org) {
        return (org.value() % 15 == 0) ? org : new Minute((org.value() / 15 + 1) * 15 );
    }
}
