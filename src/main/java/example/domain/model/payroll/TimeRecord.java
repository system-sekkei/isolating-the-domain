package example.domain.model.payroll;


import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;

public class TimeRecord {

    HourTime start;
    HourTime end;
    Minute breaks;

    public TimeRecord(HourTime start, HourTime end, Minute breaks) {
        this.start = start;
        this.end = end;
        this.breaks = breaks;
    }
}
