package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.HourTime;
import example.domain.type.time.HourTimeRange;
import example.domain.type.time.Minute;

import java.time.LocalTime;

/**
 * 日次勤怠
 */
public class AttendanceOfDay {
    Date date;
    HourTime start;
    HourTime end;
    Minute breaks;

    public AttendanceOfDay() {
        this(Date.now());
    }

    public AttendanceOfDay(Date date) {
        this.date = date;
        this.start = new HourTime("09:00");
        this.end = new HourTime("15:00");
        this.breaks = new Minute(30);
    }

    public AttendanceOfDay(Date day, TimeRecord timeRecord) {
        this(day, timeRecord.start, timeRecord.end, timeRecord.breaks);
    }

    public AttendanceOfDay(Date day, HourTime start, HourTime end, Minute breaks) {
        this.date = day;
        this.start = start;
        this.end = end;
        this.breaks = breaks;
    }

    public TimeRecord timeRecord() {
        return new TimeRecord(start, end, breaks);
    }

    public Date date() {
        return date;
    }

    public HourTime start() {
        return start;
    }

    public HourTime end() {
        return end;
    }

    public Minute breaks() {
        return breaks;
    }

    public HourAndMinute workTime() {
        HourAndMinute hourAndMinute = new HourTimeRange(normalize(start), normalize(end)).between();
        System.out.println(hourAndMinute.toString());
        Minute workingMinute = hourAndMinute.toMinute().subtract(normalize(breaks));
        return HourAndMinute.from(workingMinute);
    }

    static HourTime normalize(HourTime org) {
        LocalTime lt = org.value();
        int normalMinute = (lt.getMinute() / 15 * 15);
        return new HourTime(LocalTime.of(lt.getHour(), normalMinute));
    }

    static Minute normalize(Minute org) {
        return (org.value() % 15 == 0) ? org : new Minute((org.value() / 15 + 1) * 15);
    }
}
