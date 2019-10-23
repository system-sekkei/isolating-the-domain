package example.domain.model.timerecord.timefact;

import example.domain.type.date.Date;
import example.domain.type.time.InputTime;

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

    public static EndDateTime from(Date date, InputTime inputTime) {
        EndDate endDate = inputTime.isOverFlow() ? new EndDate(date.nextDay()) : new EndDate(date);
        EndTime endTime = new EndTime(inputTime.toClockTime());
        return new EndDateTime(endDate, endTime);
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
