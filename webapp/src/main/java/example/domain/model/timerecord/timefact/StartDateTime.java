package example.domain.model.timerecord.timefact;


import example.domain.type.date.Date;
import example.domain.type.time.InputTime;

/**
 * 勤務開始日時
 */
public class StartDateTime {

    StartDate startDate;
    StartTime startTime;

    @Deprecated
    StartDateTime() {
    }

    StartDateTime(StartDate startDate, StartTime startTime) {
        this.startDate = startDate;
        this.startTime = startTime;
    }

    public static StartDateTime from(Date startDate, InputTime inputTime) {
        return new StartDateTime(
                new StartDate(startDate),
                new StartTime(inputTime.toClockTime()));
    }

    @Override
    public String toString() {
        return startDate.toString() + " " + startTime.toString();
    }

}
