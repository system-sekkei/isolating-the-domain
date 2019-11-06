package example.domain.model.timerecord.timefact;

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

    public EndDateTime(EndDate endDate, EndTime endTime) {
        this.endDate = endDate;
        this.endTime = endTime;
    }

    public boolean isAfter(StartDateTime startDateTime) {
        if (endDate.toDate().isAfter(startDateTime.value.date())) return true;

        return endTime.value.isAfter(startDateTime.value.time());
    }

    @Override
    public String toString() {
        return endDate.toString() + " " + endTime.toString();
    }

    public String endTimeTextWith(StartDateTime startDateTime) {
        Period period = startDateTime.value.date().value().until(endDate.value().value());
        return endTime.clockTimeTextOverDays(period.getDays());
    }
}
