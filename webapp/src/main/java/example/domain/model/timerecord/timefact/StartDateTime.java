package example.domain.model.timerecord.timefact;

import example.domain.type.date.Date;
import example.domain.type.datetime.DateTime;
import example.domain.type.datetime.QuarterRoundDateTime;
import example.domain.type.time.Time;

/**
 * 勤務開始日時
 */
public class StartDateTime {

    DateTime value;

    @Deprecated
    StartDateTime() {
    }

    public StartDateTime(DateTime value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public QuarterRoundDateTime normalized() {
        return value.quarterRoundUp();
    }

    public Date date() {
        return value.date();
    }

    public Time time() {
        return value.time();
    }

    public boolean isAfter(DateTime other) {
        return value.isAfter(other);
    }

    public boolean isBefore(DateTime other) {
        return value.isBefore(other);
    }
}
