package example.domain.type.datetime;

import example.domain.type.date.Date;
import example.domain.type.time.ClockTime;


/**
 * 日時
 */
public class DateTime {

    // TODO: LocalDateTimeにする
    Date date;
    ClockTime time;

    @Deprecated
    DateTime() {
    }

    public DateTime(Date date, ClockTime time) {
        this.date = date;
        this.time = time;
    }

    public DateTime(String date, String time) {
        this.date = new Date(date);
        this.time = new ClockTime(time);
    }

    public Date date() {
        return date;
    }

    public ClockTime time() {
        return time;
    }

    @Override
    public String toString() {
        return this.date.toString() + " " + this.time.toString();
    }

}
