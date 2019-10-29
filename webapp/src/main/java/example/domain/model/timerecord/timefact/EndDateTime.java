package example.domain.model.timerecord.timefact;

import example.domain.type.date.Date;
import example.domain.type.time.ThirtyHourFormatTime;

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

    public static EndDateTime from(Date date, ThirtyHourFormatTime time) {
        EndDate endDate = time.isOverFlow() ? new EndDate(date.plusDays(1)) : new EndDate(date);
        EndTime endTime = new EndTime(time.toClockTime());
        return new EndDateTime(endDate, endTime);
    }

    public static EndDateTime from(Date date, String time) {
        return from(date, ThirtyHourFormatTime.from(time));
    }

    public boolean isAfter(StartDateTime startDateTime) {
        if (endDate.isAfter(startDateTime.startDate)) return true;

        return endTime.isAfter(startDateTime.startTime);
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
