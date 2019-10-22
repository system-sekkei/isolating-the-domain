package example.domain.model.timerecord.timefact;


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

    public static StartDateTime from(WorkDate workDate, InputTime inputTime) {
        StartDate startDate = new StartDate(workDate.value);
        StartTime startTime = new StartTime(inputTime.toClockTime());
        return new StartDateTime(startDate, startTime);
    }

    @Override
    public String toString() {
        return startDate.toString() + " " + startTime.toString();
    }

}
