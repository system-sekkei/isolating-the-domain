package example.domain.model.attendance;


import example.domain.type.time.HourAndMinute;
import example.domain.type.time.HourTime;
import example.domain.type.time.HourTimeRange;
import example.domain.type.time.Minute;

import java.time.LocalTime;

/**
 * 1日の就業時間と休憩時間
 */
public class TimeRecord {
    HourTime end;
    HourTime start;
    Minute breaks;

    public TimeRecord() {
        start = new HourTime(0,0);
        end = new HourTime(0,0);
        breaks = new Minute(0);
    }

    public TimeRecord(HourTime start, HourTime end, Minute breaks) {
        this.start = start;
        this.end = end;
        this.breaks = breaks;
    }

    public HourTime start() { return start; }
    public HourTime end() { return end; }
    public Minute breaks() { return breaks; }
    public HourAndMinute workTime() {
        HourAndMinute hourAndMinute = new HourTimeRange(normalize(start), normalize(end)).between();
        Minute workingMinute = hourAndMinute.toMinute().subtract(normalize(breaks));
        return HourAndMinute.from(workingMinute);
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
