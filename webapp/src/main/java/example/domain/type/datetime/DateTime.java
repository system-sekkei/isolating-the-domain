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

    DateTime(Date date, ClockTime time) {
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return this.date.toString() + " " + this.time.toString();
    }

}
