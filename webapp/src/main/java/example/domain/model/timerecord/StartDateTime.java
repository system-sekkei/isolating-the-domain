package example.domain.model.timerecord;


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

    public StartDateTime(WorkDate workDate, InputTime startTime) {
        this.startDate = new StartDate(workDate.value);
        this.startTime = new StartTime(startTime.toClockTime());
    }

    @Override
    public String toString() {
        return startDate.toString() + " " + startTime.toString();
    }

}
