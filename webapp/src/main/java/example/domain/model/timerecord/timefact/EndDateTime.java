package example.domain.model.timerecord.timefact;

import example.domain.type.date.Date;
import example.domain.type.time.ClockTime;

import java.time.Period;

/**
 * 勤務終了日時
 */
public class EndDateTime {

    EndDate endDate;
    EndTime endTime;

    @Deprecated
    EndDateTime() {
    }

    EndDateTime(EndDate endDate, EndTime endTime) {
        this.endDate = endDate;
        this.endTime = endTime;
    }

    public static EndDateTime from(Date date, Integer hour, Integer minute) {
        EndDate endDate = new EndDate(date.plusDays(hour / 24));
        EndTime endTime = new EndTime(new ClockTime(hour % 24, minute));
        return new EndDateTime(endDate, endTime);
    }

    public static EndDateTime from(Date date, String time) {
        String[] s = time.split(":");
        Integer hour = Integer.parseInt(s[0]);
        Integer minute = Integer.parseInt(s[1]);
        return from(date, hour, minute);
    }

    @Override
    public String toString() {
        return endDate.toString() + " " + endTime.toString();
    }

    public String endTimeTextWith(StartDate startDate) {
        Period period = startDate.value().value().until(endDate.value().value());
        return endTime.clockTimeTextOverDays(period.getDays());
    }
}
